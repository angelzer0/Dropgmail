import { useState, useEffect } from 'react';
import { useNavigate, Link, useLocation } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import UserService from '../api/UserService';

const LoginPage = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const [formData, setFormData] = useState({
        email: '',
        password: ''
    });

    const [showPassword, setShowPassword] = useState(false);

    useEffect(() => {
        if (location.state && location.state.message) {
            toast.success(location.state.message);
            navigate(location.pathname, { replace: true, state: {} });
        }
    }, [location, navigate]);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleTogglePassword = () => {
        setShowPassword(!showPassword);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!formData.email.trim()) {
            toast.error('El correo electrónico es obligatorio');
            return;
        }

        if (!formData.password.trim()) {
            toast.error('La contraseña es obligatoria');
            return;
        }

        try {
            const userData = await UserService.login(formData.email, formData.password);
            console.log('UserData:', userData); 
            if (userData.token) {
                localStorage.setItem('token', userData.token);
                localStorage.setItem('roles', JSON.stringify(userData.roles)); 
                navigate('/cursos', { state: { message: 'Has iniciado sesión correctamente' } });
            } else {
                toast.error(userData.message);
            }
        // eslint-disable-next-line no-unused-vars
        } catch (error) {
            toast.error('Se produjo un error al iniciar sesión');
        }
    };

    return (
        <div>
            <ToastContainer />
            <h2>Login</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Correo Electrónico:</label>
                    <input type="email" name="email" value={formData.email} onChange={handleInputChange} required />
                </div>
                <div>
                    <label>Contraseña:</label>
                    <div>
                        <input type={showPassword ? "text" : "password"} name="password" value={formData.password} onChange={handleInputChange} required />
                        <button type="button" onClick={handleTogglePassword}>
                            {showPassword ? 'Ocultar' : 'Mostrar'}
                        </button>
                    </div>
                </div>
                <div>
                    <button type="submit">Iniciar Sesión</button>
                </div>
            </form>
            <p>¿No tienes cuenta? <Link to="/registro">Regístrate</Link></p>
        </div>
    );
};

export default LoginPage;
