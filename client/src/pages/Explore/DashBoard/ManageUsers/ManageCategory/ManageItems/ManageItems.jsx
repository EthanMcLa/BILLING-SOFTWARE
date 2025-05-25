import './ManageItems.css';
import ItemForm from '../../../../../../components/Menubar/ItemForm/ItemForm';
import ItemList from '../../../../../../components/Menubar/ItemList/itemList';


const Manageitems = () => {
    return (
        <div className="items-container text-light">
        <div className ="left-column">
           <ItemForm />
            </div>
            <div className="right-column">
               <ItemList/>
                </div>
                
                </div>
    )
}

export default Manageitems;