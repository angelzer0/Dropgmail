import { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import UserService from '../api/UserService';

const Header = () => {
  const [isAuthenticated, setIsAuthenticated] = useState(UserService.isAuthenticated());
  const [isAdmin, setIsAdmin] = useState(UserService.isAdmin());
  const location = useLocation();

  const handleLogout = () => {
    UserService.logout();
    setIsAuthenticated(false);
  };

  useEffect(() => {
    setIsAuthenticated(UserService.isAuthenticated());
    setIsAdmin(UserService.isAdmin());

    if ((location.pathname === '/login' || location.pathname === '/registro' || location.pathname === '/') && isAuthenticated) {
      handleLogout();
    }
  }, [location.pathname, isAuthenticated]);

  return (
    <>
      <header>
        <div>
          {isAuthenticated && (
            <>
              <p>Drogmail</p>
              <nav>
                <ul>
                  <li>Inicio</li>
                  {!isAdmin && <li>Añadir Curso</li>}
                  {!isAdmin && <li>Chatroom</li>}
                  <li>Cursos</li>
                  <li>Perfil</li>
                  {isAdmin && <li>AdminPanel</li>}
                  <li onClick={handleLogout}>Cerrar sesión</li>
                </ul>
              </nav>
            </>
          )}
          {!isAuthenticated && <p>Drogmail</p>}
        </div>
      </header>
      <div>
      </div>
    </>
  );
};

export default Header;
