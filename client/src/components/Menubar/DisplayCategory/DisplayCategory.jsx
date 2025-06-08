import Category from '../Category/Category';
import './DisplayCategory.css';

const DisplayCategory = ({setSelectedCategory, setSelectedCategory, categories}) => {
    return (
        <div className="row g-3" stlye={{width: '100%', margin: 0}}>
            {categories.map(category =>(
                <div key={categories.categoryId}className="col-md-3 col-sm-6" style={{padding: '0 10px'}}>
                    <Category 
                        categoryName={category.categoryName}
                        imgUrl={category.imgUrl}
                        numberOfItems={category.items}
                        bgColor={category.bgColor}
                        isSelected={selectedCategory ===  category.categoryId}
                        onClick={() => setSelectedCategory(category.categoryId)}
                    />
                </div>
            ))}

        </div>
    )
    
}


export default DisplayCategory;