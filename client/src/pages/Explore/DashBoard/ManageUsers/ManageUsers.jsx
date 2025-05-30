import { useState } from 'react';
import UserForm from '../../../../components/Menubar/UserForm/UserForm';
import UsersList from '../../../../components/Menubar/UsersList/UsersList';
import './ManageUsers.css';
import toast from "react-hot-toast";

const ManageUsers = () => {
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(true);
    useEffect(() => {
        async function fetchUsers() {
            try {
                setLoading(true);
                const response = await fetchUsers();
                setUsers(response.data);
            } catch(error) {
                console.log(error);
                toast.error("Unable to connect");
            } finally{
                setLoading(false);
            }

        }
        fetchUsers();
    })
    
    return (
         <div className="users-container text-light">
        <div className ="left-column">
            <UserForm setUsers={setUsers}/> 
            </div>
            <div className="right-column">
                   <UsersList users={users} setUsers={setUsers}  />
                </div>
                
                </div>
    )
}

export default ManageUsers;