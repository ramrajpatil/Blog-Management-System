import React from 'react'
import {
    Navbar,
    NavbarBrand,
    NavbarText,
  } from "reactstrap";
  
  import { NavLink as ReactLink } from "react-router-dom";


const CustomFooter = () => {
  return (
    <Navbar color="dark" dark fixed='bottom' >
        <NavbarBrand tag={ReactLink} to="/home">
          Home
        </NavbarBrand>
        <NavbarText className="mx-auto">© 2024 All rights reserved.</NavbarText>
      </Navbar>
  )
}

export default CustomFooter