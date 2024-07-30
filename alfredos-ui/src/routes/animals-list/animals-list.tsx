import {
  Button,
  Card,
  CardMedia,
  CardContent,
  CardActions,
  Typography,
} from "@mui/material";
import { useAnimalData } from "../../hooks/useAnimalData";
import { useAnimalMutate } from "../../hooks/useAnimalMutate";
import FavoriteIcon from "@mui/icons-material/Favorite";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";
import AddIcon from "@mui/icons-material/Add";
import Snackbar from "@mui/material/Snackbar";
import { useNavigate } from "react-router-dom";
import "./animal-list.css";
import { Animal } from "../../types/animal";
import { useEffect, useState } from "react";

export default function AnimalsList() {
  const navigate = useNavigate();
  const { data } = useAnimalData();
  const { mutate, isSuccess } = useAnimalMutate();
  const [open, setIsOpen] = useState<boolean>(false);

  const getDate = (date: string): string => {
    const splitted = date.split("T");
    const onlyDate = splitted[0];
    const splittedDate = onlyDate.split("-");
    return `${splittedDate[2]}/${splittedDate[1]}/${splittedDate[0]}`;
  };

  const getAge = (age: number): string => {
    if (age > 0) return `${age} years old.`;
    return `Less than a year old.`;
  };

  const submit = (animal: Animal) => {
    const data = {
      id: animal.id,
      type: animal.status === "AVAILABLE" ? "ADOPTED" : "AVAILABLE",
    };
    mutate(data);
  };

  useEffect(() => {
    if (!isSuccess) return;
    setIsOpen(true);
  }, [isSuccess]);

  return (
    <>
      <Snackbar
        anchorOrigin={{ vertical: "top", horizontal: "center" }}
        open={open}
        onClose={() => setIsOpen(false)}
        message="Pet updated successfully!"
        autoHideDuration={3000}
      />
      <Button
        onClick={() => navigate("/new-animal")}
        variant="outlined"
        startIcon={<AddIcon />}
      >
        New Pet
      </Button>
      <div className="grid">
        {data?.map((animal) => (
          <Card key={animal.id} sx={{ maxWidth: 345 }}>
            <CardMedia
              sx={{ height: 140 }}
              image={animal.imageUrl}
              title={animal.name + " image"}
            />
            <CardContent>
              <Typography gutterBottom variant="h5" component="div">
                {animal.name}
              </Typography>
              <Typography
                sx={{ fontSize: 12 }}
                color="text.secondary"
                gutterBottom
              >
                {getAge(animal.age)} Born at: {getDate(animal.dateOfBirth)}
              </Typography>
              <Typography variant="body2" color="text.secondary">
                {animal.description}
              </Typography>
            </CardContent>
            <CardActions className="justify-center">
              <Button
                variant="outlined"
                size="small"
                startIcon={
                  animal.status === "AVAILABLE" ? (
                    <FavoriteBorderIcon />
                  ) : (
                    <FavoriteIcon />
                  )
                }
                onClick={() => submit(animal)}
              >
                {animal.status === "AVAILABLE" ? "Adopt" : "Adopted"}
              </Button>
            </CardActions>
          </Card>
        ))}
      </div>
    </>
  );
}
