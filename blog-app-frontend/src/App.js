import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import About from "./pages/About";
import Services from "./pages/Services";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import UserDashboard from "./pages/user-routes/UserDashboard";
import PrivateRoutes from "./components/PrivateRoutes";
import ProfileInfo from "./pages/user-routes/ProfileInfo";
import app from "./App.css";
import PostPage from "./pages/PostPage";
import UserProvider from "./contexts/UserProvider";
import Categories from "./pages/Categories";
import UpdatePost from "./pages/UpdatePost";


const App = () => {
  return (
    <UserProvider>
      <BrowserRouter>
      <ToastContainer position="bottom-right" />

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/about" element={<About />} />
        <Route path="/services" element={<Services />} />
        <Route path="/posts/:postId" element={<PostPage />} />
        <Route path="/categories/:catId"  element={<Categories/>}/>

        {/* Enclosing user inside private tag to get /private/user */}
        <Route path="/user" element={<PrivateRoutes />}>
          {/* If we put '/' again before user, it'll become absolute url then. */}
          <Route path="dashboard" element={<UserDashboard />} />
          <Route path="profile/:id" element={<ProfileInfo/>} />
          <Route path="update-blog/:postId" element={<UpdatePost/>} />
        </Route>

        
      </Routes>
    </BrowserRouter>
    </UserProvider>
  );
};

export default App;
