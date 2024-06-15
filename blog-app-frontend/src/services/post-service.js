import { myAxios, privateAxios } from "./helper";

export const createPost = (postData) => {
    // console.log(postData);
    // Using privateAxios so that we won't get forbidden error, we can pass token now.
    //"/api/user/{userId}/category/{catId}/posts"
    return privateAxios.post(`/api/user/${postData.userId}/category/${postData.catId}/posts`, postData)
    .then(response =>{
        return response.data
    });
}

// Fetch all posts
export const loadAllPosts =(pageNumber, pageSize) =>{
    // console.log("In loadAllPosts");
    return myAxios.get(`/api/posts?pageNumber=${pageNumber}&pageSize=${pageSize}&sortDir=desc`)
    .then((response) => response.data);
}

// Fetch single post of the given id.
export const loadSinglePost =(postId)=>{
    return myAxios.get(`/api/posts/${postId}`)
    .then((response) => response.data);
}

// Add comment api with userId and postId
// "/api/user/{userId}/post/{postId}/comments"

export const creatComment=(comment,userId,postId) =>{
    // console.log("In ceateComment() ");
    // console.log(userId, postId, comment);
    return privateAxios.post(`/api/user/${userId}/post/${postId}/comments`,comment)
    .then((response) => response.data);
}

// Upload post image
// "/post/image/upload/{postId}"
export const uploadPostImage = (image, postId) =>{
    let formData = new FormData();
    formData.append("image", image);

    return privateAxios.post(`/api/post/image/upload/${postId}`, formData,
        {headers: 
            {'Content-Type':'multipart/form-data'}
        })
    .then((response) => response.data);
}

// get category wise posts
// "/api/category/{catId}/posts"
export const loadByCategory = (catId) =>{
    // console.log("In loadByCategory");
    // console.log(catId);
    return privateAxios.get(`/api/category/${catId}/posts`)
    .then((response) => response.data);
}


// get user wise posts
// "/api/user/{userId}/posts"
export const loadPostsByUser = (userId) =>{
    // console.log("In loadPostsByUser");
    // console.log(userId);
    return privateAxios.get(`/api/user/${userId}/posts`)
    .then((response) => response.data);
}


// Delete post by id
// '/api/posts/{postId}"

export const deletePostById =(postId)=>{

    return privateAxios.delete(`/api/posts/${postId}`)
    .then((response) => response.data);
}


// Update post
// "/api/posts/{postId}"

export const updatePostService = (post,postId)=>{

    // console.log("In updatePostService()");
    // console.log(postId);
    // console.log(post);
    return privateAxios.put(`/api/posts/${postId}`,post)
    .then((response) => response.data);
}
