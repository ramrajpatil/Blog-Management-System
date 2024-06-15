import React, { useState } from "react";
import { useEffect } from "react";
import { loadAllPosts } from "../services/post-service";
import {
  Col,
  Row,
  Pagination,
  PaginationItem,
  PaginationLink,
  Container,
} from "reactstrap";
import Posts from "./Posts";
import { toast } from "react-toastify";

const NewFeed = () => {
  const [postContent, setPostContent] = useState({
    content: [],
    lastPage: false,
    pageNumber: "",
    pageSize: "",
    totalElements: "",
    totalPages: "",
  });

  useEffect(() => {
    // load all the posts from the server.
    loadAllPosts(0, 5)
      .then((data) => {
        setPostContent(data);
        // console.log(data);
        // From now on if we want to access anything write postContent.content
      })
      .catch((error) => {
        console.log(error);
        toast.error("Error in loading posts");
      });
  }, []);

  const changePage = (pageNumber = 0, pageSize = 5) => {

    // console.log(pageNumber);// debugging
    // console.log(postContent.pageNumber);
    if(pageNumber > postContent.pageNumber && postContent.lastPage){
      return;
    }
    if(pageNumber < postContent.pageNumber && postContent.pageNumber === 0){
      return;
    }
    loadAllPosts(pageNumber,pageSize)
      .then((data) => {
        setPostContent(data);
        // console.log(data);//Debugging.
        // After clicking on next page it should be scrolled back to top.
        window.scroll(0,0);
      })
      .catch((error) => {
        toast.error("Error in loading posts");
      });
  };

  return (
    <div className="container-fluid">
      <Row>
        <Col md={{ size: 10, offset: 1 }}>
          <h1>Blogs Count: {postContent.totalElements}</h1>

          {postContent?.content.map((post) => (
            <Posts key={post.postId} post={post} />
          ))}

          <Container className="mt-2">
            <Pagination size="md">
              <PaginationItem 

              onClick={()=> changePage(postContent.pageNumber-1)}// To  get the previous and next buttons working.
              disabled={postContent.pageNumber === 0}>
                <PaginationLink previous>Previous</PaginationLink>
              </PaginationItem>

              {[...Array(postContent.totalPages)].map((item, index) => (
                <PaginationItem
                onClick={() =>changePage(index)}
                  active={index === postContent.pageNumber}
                  key={index}
                >
                  <PaginationLink >{index + 1}</PaginationLink>
                </PaginationItem>
              ))}

              <PaginationItem
              onClick={()=> changePage(postContent.pageNumber+1)}
              disabled={postContent.lastPage === true}>
                <PaginationLink next>Next</PaginationLink>
              </PaginationItem>
            </Pagination>
          </Container>
        </Col>
      </Row>
    </div>
  );
};

export default NewFeed;
