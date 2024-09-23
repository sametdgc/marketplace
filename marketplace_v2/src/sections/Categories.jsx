import React, {useState} from "react";
import { categories } from "../constants";
import CategoryCard from "../components/CategoryCard";
import { Link } from 'react-router-dom';

const Categories = () => {

  // const [categories, setCategories] = useState([]);

  // const fetchAllCategories = () => {
  //   setCategories(categories);
  // }


  return (
    <section id="products" className="max-container max-sm:mt-12">
      <div className="flex flex-col justify-start gap-5">

        <div className="flex flex-row justify-between">
            <h2 className="text-3xl font-palanquin font-bold ">
              Shop by <span className="text-slate-gray">Category</span> 
            </h2>
            {/* <Link to="/shop">Show All</Link> */}
        </div>

        <div className="mt-3 grid lg:grid-cols-4 md:grid-cols-3 
        sm:grid-cols-2 grid-cols-1 sm:gap-4 gap-14">
          {categories.map((category, index ) => {
            return <CategoryCard  key={category.name} data={category}/>
          })}

        </div>

      </div>
    </section>
  );
};

export default Categories;
