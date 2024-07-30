import { useQuery } from "@tanstack/react-query";
import axios, { AxiosPromise } from "axios";
import { Animal } from "../types/animal";

const API_URL = "http://localhost:8080/api";

const fetchData = async (): AxiosPromise<Animal[]> => {
  const response = await axios.get<Animal[]>(API_URL.concat("/animals"));
  return response;
};

export function useAnimalData() {
  const query = useQuery({
    queryFn: fetchData,
    queryKey: ["animal-data"],
  });

  return {
    ...query,
    data: query.data?.data,
  };
}
