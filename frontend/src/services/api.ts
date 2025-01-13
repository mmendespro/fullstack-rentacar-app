const API_URL = 'http://localhost:8080/api'

export interface Customer {
  id: string
  name: string
  document: string
}

export interface ListCustomerOutput {
  customers: Customer[];
}

export interface Car {
  id: string;
  model: string;
  licensePlate: string;
  category: string;
  status: string;
}

export interface RentalOutput {
  id: string;
  car: Car;
  customer: Customer;
  startDate: string;
  endDate?: string;
  status: string;
  totalValue?: number;
}

export interface ListActiveRentalOutput {
  rentals: RentalOutput[];
}

export interface CreateRentalInput {
  carId: string;
  customerId: string;
  startDate: string;
  endDate: string;
}

export interface CreateRentalOutput {
  id: string;
}

export interface ReturnRentalInput {
  rentalId: string;
}

export interface ReturnRentalOutput {
  id: string;
  totalValue: number;
}

export const listCustomers = async (): Promise<ListCustomerOutput> => {
  const response = await fetch(`${API_URL}/customers`)
  const data = await response.json()
  return data
}

export const createCustomer = async (customer: { name: string, document: string }): Promise<{ id: string }> => {
  const response = await fetch(`${API_URL}/customers`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(customer),
  })
  return response.json()
}

export const listRentals = async (): Promise<ListActiveRentalOutput> => {
  const response = await fetch(`${API_URL}/rentals`)
  return response.json()
}

export const createRental = async (input: CreateRentalInput): Promise<CreateRentalOutput> => {
  const response = await fetch(`${API_URL}/rentals`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(input),
  })
  return response.json()
}

export const returnRental = async (input: ReturnRentalInput): Promise<ReturnRentalOutput> => {
  const response = await fetch(`${API_URL}/rentals/return`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(input),
  })
  return response.json()
}
