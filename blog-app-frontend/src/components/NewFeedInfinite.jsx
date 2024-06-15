import React, { useState } from "react";
import { useEffect } from "react";
import { loadAllPosts } from "../services/post-service";
import {
  Col,
  Row,
  Container,
  Button,
} from "reactstrap";
import Posts from "./Posts";
import { toast } from "react-toastify";
import InfiniteScroll from "react-infinite-scroll-component";
import { deletePostById } from "../services/post-service";


const NewFeedInfinite = () => {
  const [postContent, setPostContent] = useState({
    content: [],
    lastPage: false,
    pageNumber: "",
    pageSize: "",
    totalElements: "",
    totalPages: "",
  });

  // To tract the current page
  const [currentPage, setCurrentPage] = useState(0);

  useEffect(() => {
    
    changePage(currentPage);
  }, [currentPage]);

  const changePage = (pageNumber = 0, pageSize = 5) => {
    // console.log(pageNumber);// debugging
    // console.log(postContent.pageNumber);
    if (pageNumber > postContent.pageNumber && postContent.lastPage) {
      return;
    }
    if (pageNumber < postContent.pageNumber && postContent.pageNumber === 0) {
      return;
    }
    loadAllPosts(pageNumber, pageSize)
      .then((data) => {
        // To push new data into the existing postContent
        setPostContent({
          content: [...postContent.content, ...data.content],
          lastPage: data.lastPage,
          pageNumber: data.pageNumber,
          pageSize: data.pageSize,
          totalElements: data.totalElements,
          totalPages: data.totalPages,
        });
        console.log(data);//Debugging.
        // After clicking on next page it should be scrolled back to top.
        // window.scroll(0, 0); // Uncomment this when you dont want to use infite scroll.
      })
      .catch((error) => {
        toast.error("Error in loading posts");
      });
  };


    // Function to delete post
    const deletePost = (post) => {
      // Going to delete post.
  
      deletePostById(post.postId)
        .then((data) => {
          console.log(data);
          toast.success("Post: " + post.title + " deleted succefully !!");
          
          // refreshing feed after deletion.
        let newPostContent =  postContent.content.filter(p => p.postId !== post.postId)
            setPostContent({...postContent,content: newPostContent});
        })
        .catch((error) => {
          console.log(error);
          toast.error("Post deletion failed !!!");
        });
    };

  // Infinite function implementation
  const changePageInfinite = () => {
    console.log("Page changed");

    setCurrentPage(currentPage + 1);
  };

  const goToTop =()=>{
    window.scroll(0,0);
  }


  return (
    <div className="container-fluid">
      <Row>
        <Col md={{ size: 12 }}>
          <h1>Blogs Count: ({postContent.totalElements})</h1>

          <InfiniteScroll
            dataLength={postContent.content.length}
            next={changePageInfinite}
            hasMore={!postContent.lastPage}
            loader={<h4>Loading...</h4>}
            endMessage={
              <p style={{ textAlign: "center" }}>
                <b>Yay! You have seen it all</b>
              </p>
            }
          >
            {postContent.content.map((post) => (
              <Posts deletePost={deletePost} key={post.postId} post={post} />
            ))}
            
            {
              postContent.lastPage && (
                <Container>
                  <Button color="primary" className="mt-4" onClick={goToTop}>Go to Top</Button>
                </Container>
              )
            }

          </InfiniteScroll>

        </Col>
      </Row>
    </div>
  );
};

export default NewFeedInfinite;
