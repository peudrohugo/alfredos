import { ChangeEvent, useEffect, useState } from "react";
import { useAnimalCreate } from "../../hooks/useAnimalCreate";
import {
  Button,
  TextField,
  Box,
  Select,
  MenuItem,
  SelectChangeEvent,
  InputLabel,
  FormControl,
  FormControlLabel,
  Checkbox,
  FormGroup,
} from "@mui/material";
import CheckIcon from "@mui/icons-material/Check";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import { DemoContainer } from "@mui/x-date-pickers/internals/demo";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import Snackbar from "@mui/material/Snackbar";
import "./new-animal.css";
import { Dayjs } from "dayjs";
import { useNavigate } from "react-router-dom";

export default function NewAnimal() {
  const navigate = useNavigate();
  const { mutate, isSuccess } = useAnimalCreate();
  const [name, setName] = useState<string>("");
  const [description, setDescription] = useState<string>("");
  const [imageUrl, setImageUrl] = useState<string>("");
  const [category, setCategory] = useState<string>("");
  const [dateOfBirth, setDateOfBirth] = useState<Dayjs | null>(null);
  const [adopted, setAdopted] = useState<boolean>(false);
  const [open, setIsOpen] = useState<boolean>(false);

  const submit = () => {
    const data = {
      name,
      description,
      imageUrl,
      category,
      dateOfBirth: dateOfBirth?.toDate()!,
      status: adopted ? "ADOPTED" : "AVAILABLE",
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
        message="Pet registered successfully!"
        autoHideDuration={3000}
      />
      <Button
        onClick={() => navigate("/animals-list")}
        variant="outlined"
        startIcon={<ArrowBackIcon />}
      >
        Back
      </Button>
      <Box sx={{ display: "grid" }}>
        <FormGroup>
          <TextField
            required
            className="mt-mb-half"
            label="Name"
            value={name}
            onChange={(event: ChangeEvent<HTMLInputElement>) =>
              setName(event.target.value)
            }
          />
          <TextField
            required
            className="mb-half"
            label="Image URL"
            value={imageUrl}
            onChange={(event: ChangeEvent<HTMLInputElement>) =>
              setImageUrl(event.target.value)
            }
          />
          <FormControl>
            <InputLabel id="category-label">Category</InputLabel>
            <Select
              labelId="category-label"
              value={category}
              label="Category"
              onChange={(event: SelectChangeEvent) =>
                setCategory(event.target.value)
              }
            >
              <MenuItem value={"DOG"}>Dog</MenuItem>
              <MenuItem value={"CAT"}>Cat</MenuItem>
              <MenuItem value={"BIRD"}>Bird</MenuItem>
            </Select>
          </FormControl>
          <FormControlLabel
            control={
              <Checkbox
                checked={adopted}
                onChange={() => setAdopted(!adopted)}
              />
            }
            label="Adopted"
          />
        </FormGroup>
        <TextField
          label="Description"
          multiline
          rows={4}
          value={description}
          onChange={(event: ChangeEvent<HTMLInputElement>) =>
            setDescription(event.target.value)
          }
        />
        <LocalizationProvider dateAdapter={AdapterDayjs}>
          <DemoContainer components={["DatePicker"]}>
            <DatePicker
              format="DD/MM/YYYY"
              value={dateOfBirth}
              onChange={(value) => setDateOfBirth(value)}
              label="Date of Birth"
            />
          </DemoContainer>
        </LocalizationProvider>
        <Button onClick={submit} variant="outlined" startIcon={<CheckIcon />}>
          Register Pet
        </Button>
      </Box>
    </>
  );
}
