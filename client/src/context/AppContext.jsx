import { createContext, useEffect, useState } from "react";
import {fetchCategories} from "../Service/CategoryService.js";
import { fetchItems } from "../Service/ItemService.js";

export const AppContext = createContext(null);

export const AppContextProvider = (props) => {
    const [categories, setCategories] = useState([]);
    const [itemsData, setItemsData] = useState([]);
    const [auth, setAuth] = useState({
        token: localStorage.getItem('token'), 
        role: localStorage.getItem('role')
    });
    
    useEffect(() => {
        async function loadData() {
          
            try {
                      if(localStorage.getItem("token") && localStorage.getItem("role")) {
                setAuth({
                    token: localStorage.getItem("token"),
                    role: localStorage.getItem("role")
                });
            }
                if (auth.token) {
                    console.log("Fetching with token:", auth.token);
                    const response = await fetchCategories();
                    setCategories(response.data);
                    
                    try {
                        const itemResponse = await fetchItems();
                        setItemsData(itemResponse.data);
                    } catch (itemError) {
                        console.error("Error fetching items:", itemError);
                  
                    }
                }
            } catch (error) {
                console.error("Error fetching categories:", error);
            }
        }
        loadData();
    }, [auth.token]); 
    
    const setAuthData = (token, role) => {  
        localStorage.setItem('token', token);
        localStorage.setItem('role', role);
        setAuth({ token, role });  
    }

    const contextValue = {
        categories,
        setCategories,
        auth,
        setAuthData,
        itemsData,
        setItemsData  
    }

    return (
        <AppContext.Provider value={contextValue}>
            {props.children}
        </AppContext.Provider>
    );
}

    