import { useState } from 'react'
import { CustomerList } from './components/CustomerList'
import { CustomerForm } from './components/CustomerForm'

function CustomerPage() {

    const [refreshCustomers, setRefreshCustomers] = useState(false)

    return (
        <div className="space-y-6">
            <CustomerForm onSuccess={() => setRefreshCustomers(!refreshCustomers)} />
            <CustomerList key={refreshCustomers.toString()} />
        </div>
    );
}

export default CustomerPage;