import React, { useEffect, useState } from "react";
import Base from "../../components/Base";
import AddPost from "../../components/AddPost";
import { Container } from "reactstrap";
import NewFeedInfinite from "../../components/NewFeedInfinite";
import NewFeed from "../../components/NewFeed";
import { getCurrentUserDetail } from "../../auth/index-auth";
import { deletePostById, loadPostsByUser } from "../../services/post-service";
import Posts from "../../components/Posts";
import { toast } from "react-toastify";



const UserDashboard = () => {
  const [posts, setPosts] = useState([]);

  const [user, setUser] = useState([])
  
  useEffect(() => {
    // console.log(userId);
    console.log(getCurrentUserDetail());
    setUser(getCurrentUserDetail());
    loadPostData();
  }, []);

  const loadPostData = () =>{
    console.log(user);
    loadPostsByUser(getCurrentUserDetail()?.id)
      .then((data) => {
        console.log(data);
        setPosts([...data]);
      })
      .catch((error) => {
        console.log(error);
      });
  }

  // Function to delete post
  const deletePost = (post) => {
    // Going to delete post.

    deletePostById(post.postId)
      .then((data) => {
        console.log(data);
        toast.success("Post: " + post.title + " deleted successfully !!");
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
      <Container>
        <AddPost />
      </Container>
      <Container className="mt-2">
        <h1 style={{ textAlign: "center" }}>Posts Count: ({posts.length})</h1>

        {posts.map((p, index) => {
          return <Posts deletePost={deletePost} key={index} post={p} />;
        })}
      </Container>
    </Base>
  );
};

export default UserDashboard;
