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

    const [cartItems, setCartItems] = useState( []);

const addToCart = (item) => {
    console.log("Adding to cart:", item);
    
    // Make sure we have all required fields
    if (!item.itemId || !item.name || !item.price) {
        console.error("Invalid item:", item);
        return;
    }
    
    setCartItems(prevCart => {
        // Check if item already exists
        const existingItemIndex = prevCart.findIndex(cartItem => cartItem.itemId === item.itemId);
        
        if (existingItemIndex >= 0) {
            // Item exists, update quantity
            const updatedCart = [...prevCart];
            updatedCart[existingItemIndex] = {
                ...updatedCart[existingItemIndex],
                quantity: updatedCart[existingItemIndex].quantity + 1
            };
            console.log("Updated cart:", updatedCart);
            return updatedCart;
        } else {
            // Item doesn't exist, add it
            const newCart = [...prevCart, {...item, quantity: 1}];
            console.log("New cart:", newCart);
            return newCart;
        }
    });
};



const removeFromCart = (itemId) => {
    setCartItems(cartItems.filter(item => item.itemId !== itemId));
}

const updateQuantity = (itemId, newQuantity) => {
    setCartItems(cartItems.map(item => item.itemId === itemId ? {...item, quantity: newQuantity} : item ))

}

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
        itemsData,
        setAuthData,
        setItemsData,
        addToCart,
        cartItems,
        removeFromCart,
        updateQuantity,
    }

    return (
        <AppContext.Provider value={contextValue}>
            {props.children}
        </AppContext.Provider>
    );
}

    