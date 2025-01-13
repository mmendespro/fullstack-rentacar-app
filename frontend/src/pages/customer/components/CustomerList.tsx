import { useEffect, useState } from 'react'
import { listCustomers, Customer } from '../../../services/api'

export function CustomerList() {
  const [customers, setCustomers] = useState<Customer[]>([])

  useEffect(() => {
    const fetchCustomers = async () => {
      const data = await listCustomers();
      setCustomers(data.customers)
    }
    fetchCustomers()
  }, [])

  return (
    <div className="bg-white rounded-lg shadow p-6">
      <h2 className="text-xl font-semibold mb-4">Customers</h2>
      <table className="w-full">
        <thead>
          <tr className="border-b">
            <th className="text-left p-2">Name</th>
            <th className="text-left p-2">Document</th>
          </tr>
        </thead>
        <tbody>
          {customers.map(customer => (
            <tr key={customer.id} className="border-b hover:bg-gray-50">
              <td className="p-2">{customer.name}</td>
              <td className="p-2">{customer.document}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}
