import CartItems from '../../components/Menubar/CartItems/CartItems';
import CartSummary from '../../components/Menubar/CartSummary/CartSummary';
import CustomerForm from '../../components/Menubar/Customerform/CustomerForm';
import DisplayCategory from '../../components/Menubar/DisplayCategory/DisplayCategory';
import DisplayItems from '../../components/Menubar/DisplayItems/DisplayItems';
import { AppContext } from '../../context/AppContext';
import './Explore.css';
import {useContext} from "react";

const Explore = () => {
    const {categories} = useContext(AppContext);
    console.log(categories);   
   
    return (
       <div className="explore-container text-light">
        <div className="left-column">

            <div className="first-row" style={{overflowY: 'auto'}}>
                <DisplayCategory categories={categories} />
            </div>
            <hr className="horizontal-line"/> 
            <div className="second-row"style={{overflowY: 'auto'}}>
                <DisplayItems/>
            </div>
        </div>
        <div className="right-column d-flex flex-column">
        <div className="customer-form-container" style={{height: '15%'}}>
               <CustomerForm/>
        </div>
        
        
       
        <hr className="my-3 text-light"/> 
        <div className="cart-items-container" style={{height: '55%', overflowY: 'auto'}}>
            <CartItems/> 

        </div>
        <div className="cart-summary-container" style={{height: '30%'}}>
            <CartSummary/>
         </div>
        </div>
       </div>
    )
}

export default Explore;