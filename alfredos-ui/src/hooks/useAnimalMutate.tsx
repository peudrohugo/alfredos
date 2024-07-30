import { useMutation, useQueryClient } from "@tanstack/react-query";
import { Animal } from "../types/animal";
import axios, { AxiosPromise } from "axios";

const API_URL = "http://localhost:8080/api";

const patchData = async (data: {
  id: number;
  type: string;
}): AxiosPromise<Animal> => {
  return await axios.patch(API_URL.concat(`/animals/${data.id}`), data.type);
};

export function useAnimalMutate() {
  const queryClient = useQueryClient();
  const mutate = useMutation({
    mutationFn: patchData,
    onSuccess: () => {
      queryClient.invalidateQueries(["animal-data"]);
    },
  });

  return mutate;
}
