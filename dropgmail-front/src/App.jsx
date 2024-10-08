import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Inicio from './pages/Inicio';
import Header from './components/Header';
import Footer from './components/Footer';
import LoginPage from './pages/LoginPage';
import RegistrationPage from './pages/RegistrationPage';

const App = () => {
  return (
    <Router>
      <Header />
      <Routes>
        <Route path="/" element={<Inicio />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/registro" element={<RegistrationPage />} />
        {/* Redirige a la página de Inicio para todas las rutas no definidas */}
        <Route path="*" element={<Navigate to="/" />} />
      </Routes> 
      <Footer />
    </Router>
  );
};

export default App;
