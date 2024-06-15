import React from 'react'
import Base from '../components/Base'
import { getCurrentUserDetail } from '../auth/index-auth'

const About = () => {
  const user = getCurrentUserDetail();
  console.log(user);


  return (
    <Base>
      <h1>This is About page</h1>
      <p>We are buiding a blog.</p>
      <h1>
        Welcome: 
        {user && user.name}
      </h1>
    </Base>
  );
}

export default About