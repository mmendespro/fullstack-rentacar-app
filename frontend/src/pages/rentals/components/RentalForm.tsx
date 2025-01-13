import { useState } from 'react';
import { createRental, CreateRentalInput } from '../../../services/api';

interface RentalFormProps {
  onSuccess: () => void;
}

export function RentalForm({ onSuccess }: RentalFormProps) {
  const [formData, setFormData] = useState<CreateRentalInput>({
    carId: '',
    customerId: '',
    startDate: '',
    endDate: ''
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      await createRental(formData);
      onSuccess();
    } catch (err) {
      setError('Erro ao criar aluguel');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div>
        <label className="block text-sm font-medium">ID do Carro</label>
        <input
          type="text"
          value={formData.carId}
          onChange={(e) => setFormData({ ...formData, carId: e.target.value })}
          className="mt-1 block w-full rounded-md border p-2"
          required
        />
      </div>

      <div>
        <label className="block text-sm font-medium">ID do Cliente</label>
        <input
          type="text"
          value={formData.customerId}
          onChange={(e) => setFormData({ ...formData, customerId: e.target.value })}
          className="mt-1 block w-full rounded-md border p-2"
          required
        />
      </div>

      <div>
        <label className="block text-sm font-medium">Data de Início</label>
        <input
          type="date"
          value={formData.startDate}
          onChange={(e) => setFormData({ ...formData, startDate: e.target.value })}
          className="mt-1 block w-full rounded-md border p-2"
          required
        />
      </div>

      <div>
        <label className="block text-sm font-medium">Data de Término</label>
        <input
          type="date"
          value={formData.endDate}
          onChange={(e) => setFormData({ ...formData, endDate: e.target.value })}
          className="mt-1 block w-full rounded-md border p-2"
          required
        />
      </div>

      {error && <div className="text-red-500">{error}</div>}

      <button
        type="submit"
        disabled={loading}
        className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 disabled:opacity-50"
      >
        {loading ? 'Criando...' : 'Criar Aluguel'}
      </button>
    </form>
  );
}
