import React, { useEffect, useState } from "react";
import Base from "../components/Base";
import { useParams } from "react-router-dom";
import { Col, Button, Row, Container } from "reactstrap";
import CategorySideMenu from "../components/CategorySideMenu";
import { loadByCategory } from "../services/post-service";
import { toast } from "react-toastify";
import Posts from "../components/Posts";
import { deletePostById } from "../services/post-service";


const Categories = () => {
  const { catId } = useParams();

  const [posts, setPosts] = useState([]);

  useEffect(() => {

    // console.log(catId);
    loadPostData();
    
  },[catId]);

  const loadPostData =()=>{
    loadByCategory(catId)
      .then((data) => {
        // console.log(data);
        setPosts([...data]);
      })
      .catch((error) => {
        console.log(error);
        toast.error("Error in loading posts.")
      });
  }

  const goToTop =()=>{
    window.scroll(0,0);
  }

// Function to delete post
const deletePost = (post) => {
    // Going to delete post.

    deletePostById(post.postId)
      .then((data) => {
        console.log(data);
        toast.success("Post: " + post.title + " deleted succefully !!");
        loadPostData();

        // let newPosts = posts.filter( p => p.postId != post.postId);

        // setPosts({...newPosts});
      })
      .catch((error) => {
        console.log(error);
        toast.error("Post deletion failed !!!");
      });
  };


  return (
    <Base>
      <Container className="mt-5">
        {/* <NewFeed /> */}
        <Row>
          <Col md={3} className="mt-5 pt-3">
          
            <CategorySideMenu />
          </Col>
          <Col md={9}>
          <h1>Blogs Count: ({posts.length})</h1>
          {
            posts && posts?.map((p, index) =>{
                return (
                    <Posts deletePost={deletePost} key={index} post={p} />
                )
            })
          }
          {
            posts.length <=0 ? <h1>No posts have been added to this category yet.</h1> : ''
          }
          </Col>
        </Row>
        <Container className="container-fluid">
                  <Button color="primary" className="mt-3 mb-3" onClick={goToTop}>Go to Top</Button>
                </Container>
      </Container>
    </Base>
  );
};

export default Categories;
