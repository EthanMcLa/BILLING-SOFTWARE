import CategoryForm from '../../../../../components/Menubar/CategoryForm/CategoryForm';
import CategoryList from '../../../../../components/Menubar/CategoryList/CategoryList';
import './ManageCategory.css';

const ManageCategory = () => {
    return (
      <div className="category-container text-light">
        <div className ="left-column">
            <CategoryForm/> 
            </div>
            <div className="right-column">
                  <CategoryList/>
                </div>
                
                </div>
    )
}


export default ManageCategory;