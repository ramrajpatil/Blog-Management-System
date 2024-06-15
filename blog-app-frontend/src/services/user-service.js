import { myAxios } from "./helper";

export const signUp = (user) => {
  return myAxios
    .post("/api/auth/register", user)
    .then((response) => response.data);
};


export const loginUser = (loginDetail) =>{
    return myAxios
    .post("/api/auth/login",loginDetail)
    .then((response) => response.data)// We will get token and user here.
}