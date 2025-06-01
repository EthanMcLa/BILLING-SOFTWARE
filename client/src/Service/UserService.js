import axios from 'axios'

export const addUsers = async (userData) => {
  return await axios.post('http://localhost:8080/api/v1.0/admin/register', userData, {
    headers: {
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    }
  });
};
export const deleteUser = async (id) => {
    return await axios.delete(`http://localhost:8080/api/v1.0/admin/users/${id}`, { 
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    });
}


export const fetchUsers = async () => {
  return await axios.get('http://localhost:8080/api/v1.0/admin/users', {
    headers: {
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    }
  });
};