export type Animal = {
  id: number;
  name: string;
  description: string;
  imageUrl: string;
  category: string;
  dateOfBirth: string;
  age: number;
  status: string;
};

export type AnimalRequest = {
  name: string;
  description: string;
  imageUrl: string;
  category: string;
  dateOfBirth: Date;
  status: string;
};
