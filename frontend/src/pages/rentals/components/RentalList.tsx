import { useEffect, useState } from 'react';
import { listRentals, returnRental, RentalOutput } from '../../../services/api';

export function RentalList() {
  const [rentals, setRentals] = useState<RentalOutput[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchRentals() {
      try {
        const data = await listRentals();
        setRentals(data.rentals);
      } catch (error) {
        console.error('Error fetching rentals:', error);
      } finally {
        setLoading(false);
      }
    }
    fetchRentals();
  }, []);

  if (loading) return <div>Loading...</div>;

  return (
    <div className="space-y-4">
      {rentals.map((rental) => (
        <div key={rental.id} className="p-4 border rounded-lg">
          <div className="flex justify-between">
            <div>
              <h3 className="font-bold">{rental.car.model} - {rental.car.licensePlate}</h3>
              <p>Cliente: {rental.customer.name}</p>
              <p>Início: {new Date(rental.startDate).toLocaleDateString()}</p>
              <p>Status: {rental.status}</p>
            </div>
            <div>
              <button 
                className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600"
                onClick={() => handleReturn(rental.id)}
              >
                Devolver
              </button>
            </div>
          </div>
        </div>
      ))}
    </div>
  );

  async function handleReturn(rentalId: string) {
    try {
      await returnRental({ rentalId });
      setRentals((prev) => prev.filter((r) => r.id !== rentalId));
    } catch (error) {
      console.error('Error returning rental:', error);
    }
  }
}
