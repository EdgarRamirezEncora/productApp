import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './components/App.tsx'
import './index.css'
import {
    BrowserRouter,
    Route,
    Routes
} from "react-router-dom";
import ProductList from "./components/ProductList/ProductList.tsx";
import {SnackbarProvider} from "notistack";


ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
      <SnackbarProvider
          autoHideDuration={5000}
      >
          <BrowserRouter>
              <Routes>
                  <Route path="/" element={<ProductList/>}/>
                  <Route path="/add-product" element={<App/>}></Route>
              </Routes>
          </BrowserRouter>
      </SnackbarProvider>
  </React.StrictMode>,
)
