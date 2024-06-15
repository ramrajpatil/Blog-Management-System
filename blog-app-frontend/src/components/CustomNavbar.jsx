import React, { useContext, useEffect, useState } from "react";
import {
  Collapse,
  Navbar,
  NavbarToggler,
  NavbarBrand,
  Nav,
  NavItem,
  NavLink,
  UncontrolledDropdown,
  DropdownToggle,
  DropdownMenu,
  DropdownItem,
} from "reactstrap";

import { NavLink as ReactLink, useNavigate } from "react-router-dom";
import { doLogout, getCurrentUserDetail, isLoggedIn } from "../auth/index-auth";
import userContext from "../contexts/userContext";
import { toast } from "react-toastify";

function CustomNavbar(args) {

  const userContextData = useContext(userContext)

  const [isOpen, setIsOpen] = useState(false);

  const toggle = () => setIsOpen(!isOpen);

  const [login, setLogin] = useState(false);

  const [user, setUser] = useState(undefined);

  const navigator = useNavigate();


  useEffect(() => {
    setLogin(isLoggedIn());
    setUser(getCurrentUserDetail());
  }, [login]);


  const logout=()=>{
    doLogout(()=>{
      //Logged out and redirect
      setLogin(false);
      

      userContextData.setUser({
        data:null,
        login:false
      })
      toast.success("User logged Out !!!")
      navigator("/")
    });
    
  }

  return (
    <Navbar color="dark" dark expand="md" {...args} className="px-4">
      <NavbarBrand tag={ReactLink} to="/">
        MyBlogs
      </NavbarBrand>
      <NavbarToggler onClick={toggle} />
      <Collapse isOpen={isOpen} navbar>
        <Nav className="me-auto" navbar>
          <NavItem>
            <NavLink tag={ReactLink} to="/" className="nav-link">
              New Feed
            </NavLink>
          </NavItem>
          <NavItem>
            <NavLink tag={ReactLink} to="/about" className="nav-link">
              About
            </NavLink>
          </NavItem>
          <NavItem>
            <NavLink tag={ReactLink} to="/services" className="nav-link">
              Services
            </NavLink>
          </NavItem>

          <UncontrolledDropdown nav inNavbar>
            <DropdownToggle nav caret className="nav-link">
              More
            </DropdownToggle>
            <DropdownMenu end>
              <DropdownItem>ContactUs</DropdownItem>
              <DropdownItem divider />
              <DropdownItem>Reset</DropdownItem>
            </DropdownMenu>
          </UncontrolledDropdown>
        </Nav>

        <Nav navbar>
        {
          login && (
            <>
            <NavItem>
            <NavLink tag={ReactLink} to={`/user/profile/${user.id}`} 
            className="nav-link"
            
            >
              Profile
            </NavLink>
          </NavItem>

          <NavItem>
            <NavLink tag={ReactLink} to="/user/dashboard" className="nav-link">
              Dashboard
            </NavLink>
          </NavItem>

          <NavItem>
            <NavLink  
            className="nav-link"
            onClick={logout}
            >
              Logout
            </NavLink>
          </NavItem>
          </>
          )

        }

        {
          !login && (
            <>
            <NavItem>
            <NavLink tag={ReactLink} to="/login" className="nav-link">
              Login
            </NavLink>
          </NavItem>
          <NavItem>
            <NavLink tag={ReactLink} to="/signup" className="nav-link">
              Signup
            </NavLink>
          </NavItem>
            </>
          )
        }

          
        </Nav>
      </Collapse>
    </Navbar>
  );
}

export default CustomNavbar;
