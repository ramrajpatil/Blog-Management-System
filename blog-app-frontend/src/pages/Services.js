import React, { useContext } from "react";
import Base from "../components/Base";
import { getCurrentUserDetail } from "../auth/index-auth";

const Services = () => {
  
  const user = getCurrentUserDetail();
  console.log(user);


  return (
    <Base>
      <h1>This is services page</h1>
      <p>We are buiding a blog.</p>
      <h1>
        Welcome user:
        {user && user.name}
      </h1>
    </Base>
  );
};

export default Services;
