import { useMutation } from "@tanstack/react-query";
import { Animal, AnimalRequest } from "../types/animal";
import axios, { AxiosPromise } from "axios";

const API_URL = "http://localhost:8080/api";

const postData = async (data: AnimalRequest): AxiosPromise<Animal> => {
  return await axios.post(API_URL.concat("/animals"), data);
};

export function useAnimalCreate() {
  const mutate = useMutation({
    mutationFn: postData,
  });

  return mutate;
}
