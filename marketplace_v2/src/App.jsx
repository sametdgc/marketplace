import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Hero, CustomerReviews, SpecialOffers, Categories, Subscribe, Footer, SuperQuality, Services, NewProducts } from './sections/index.js';
import Nav from './components/Nav.jsx';
import ProductDetails from './pages/ProductDetails';
import ShopPage from './pages/ShopPage.jsx';
import LoginPage from './pages/LoginPage.jsx';


const App = () => {
  return (
    <Router>
      <Nav />
      <Routes>
        {/* Home page */}
        <Route 
          path="/" 
          element={
            <main className='relative'>
              <section className='xl:padding-l wide:padding-r padding-b'>
                <Hero />
              </section>
              <section className='padding'>
                <Categories />
              </section>
              <section className='padding'>
                <NewProducts />
              </section>
              <section className='padding'>
                <SuperQuality />
              </section>
              <section className='padding-x py-10'>
                <Services />
              </section>
              {/* <section className='padding'>
                <SpecialOffers />
              </section>
              <section className='bg-pale-blue padding'>
                <CustomerReviews />
              </section> */}
              {/* <section className='padding-x sm:py-32 py-16 w-full'>
                <Subscribe />
              </section> */}
              <section className=' bg-black padding-x padding-t pb-8'>
                <Footer />
              </section>
            </main>
          }
        />
        
        {/* Product details page */}
        <Route path="/product/:id" element={<ProductDetails />} />
        <Route path="/login" element={<LoginPage/>}/>
        <Route path="/shop" element={<ShopPage/>}/>
      </Routes>
    </Router>
  );
};

export default App;
