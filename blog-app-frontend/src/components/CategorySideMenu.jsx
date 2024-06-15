import React, { useEffect, useState } from "react";
import { ListGroup, ListGroupItem } from "reactstrap";
import { loadAllCategories } from "../services/category-service";
import { Link } from "react-router-dom";

const CategorySideMenu = () => {
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    loadAllCategories()
      .then((data) => {
        setCategories([...data]);
        console.log(data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  return (
    <div>
      <ListGroup>
        <ListGroupItem tag={Link} to='/' action={true} className="border-0">
          All Blogs
        </ListGroupItem>

        {categories &&
          categories.map((c, index) => {
            return <ListGroupItem 
            tag={Link} to={"/categories/"+c.id}
            className="border-0 mt-1"
            action={true}
            key={index}>
                {c.categoryTitle}
                </ListGroupItem>;
          })}
      </ListGroup>
    </div>
  );
};

export default CategorySideMenu;
