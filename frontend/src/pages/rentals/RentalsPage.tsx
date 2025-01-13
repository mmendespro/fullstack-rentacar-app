import { useState } from 'react';
import { RentalForm } from './components/RentalForm';
import { RentalList } from './components/RentalList';

export function RentalsPage() {
  const [refreshKey, setRefreshKey] = useState(0);

  const handleRentalCreated = () => {
    setRefreshKey(prev => prev + 1);
  };

  return (
    <div className="space-y-8 p-6">
      <h1 className="text-2xl font-bold">Gerenciamento de Aluguéis</h1>
      
      <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
        <div>
          <h2 className="text-xl font-semibold mb-4">Novo Aluguel</h2>
          <RentalForm onSuccess={handleRentalCreated} />
        </div>
        
        <div>
          <h2 className="text-xl font-semibold mb-4">Aluguéis Ativos</h2>
          <RentalList key={refreshKey} />
        </div>
      </div>
    </div>
  );
}

export default RentalsPage;
