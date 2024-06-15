//doLogin=> data => set to localstorage

export const doLogin = (data, next) => {
  // We cannot save the data in localStorage in object format
  // We have to convert in into stirg using JSON.stringfy().
  localStorage.setItem("data", JSON.stringify(data));
  next(); // callback function, if user to be redirected to another page after login
};

// isLoggedIn or isAuthenticated.
// if the token is present in local storage
export const isLoggedIn = () => {
  let data = localStorage.getItem("data");

  if (data == null) return false;
  else return true;
};

//doLogout => remove data from localstorage.

export const doLogout = (next) => {
  localStorage.removeItem("data");
  next(); // callback function: If we want the user to be redirected to the login or homepage again.
};

// Get currentUser
export const getCurrentUserDetail = () => {
  // convert the stringyfied data to object first using JSON.parse()
  if (isLoggedIn()) return JSON.parse(localStorage.getItem("data")).user;
  else return undefined;
};

export const getToken = () => {
  if (isLoggedIn()) return JSON.parse(localStorage.getItem("data")).token;
  else return null;
};
