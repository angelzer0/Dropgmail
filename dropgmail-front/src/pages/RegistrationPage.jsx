import { useState } from 'react';
import UserService from '../api/UserService';
import { useNavigate, Link } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const RegistrationPage = () => {
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        nombre: '',
        username: '',
        email: '',
        password: ''
    });

    const [errors, setErrors] = useState({});
    const [showPassword, setShowPassword] = useState(false);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
        setErrors({ ...errors, [name]: '' });
    };

    const handleTogglePassword = () => {
        setShowPassword(!showPassword);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const validationErrors = {};
        if (!formData.nombre.trim()) {
            validationErrors.nombre = 'El nombre es obligatorio';
        }
        if (!formData.email.trim()) {
            validationErrors.email = 'El correo electrónico es obligatorio';
        }
        if (!formData.password.trim()) {
            validationErrors.password = 'La contraseña es obligatoria';
        } else if (formData.password.trim().length < 8) {
            validationErrors.password = 'La contraseña debe tener al menos 8 caracteres';
        } else if (!/[A-Z]/.test(formData.password)) {
            validationErrors.password = 'La contraseña debe contener al menos una letra mayúscula';
        }
        if (Object.keys(validationErrors).length > 0) {
            setErrors(validationErrors);
            return;
        }

        try {
            const response = await UserService.register(formData);
            if (response.statusCode === 200) {
                setFormData({
                    nombre: '',
                    email: '',
                    password: ''
                });
                navigate('/login', { state: { message: 'Te has registrado correctamente' } });
            } else if (response.statusCode === 400) {
                setErrors({ email: response.message });
            }
        } catch (error) {
            console.error('Error al registrar usuario:', error);
            toast.error('Se produjo un error al registrar al usuario');
        }
    };

    return (
        <div>
            <ToastContainer />
            <h2>Registro</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Nombre:</label>
                    <input type="text" name="nombre" value={formData.nombre} onChange={handleInputChange} required />
                    {errors.nombre && <div>{errors.nombre}</div>}
                </div>
                <div>
                    <label>Correo Electrónico:</label>
                    <input type="email" name="email" value={formData.email} onChange={handleInputChange} required />
                    {errors.email && <div>{errors.email}</div>}
                </div>
                <div>
                    <label>Contraseña:</label>
                    <div>
                        <input type={showPassword ? "text" : "password"} name="password" value={formData.password} onChange={handleInputChange} required />
                        <button type="button" onClick={handleTogglePassword}>
                            {showPassword ? 'Ocultar' : 'Mostrar'}
                        </button>
                    </div>
                    {errors.password && <div>{errors.password}</div>}
                </div>
                <button type="submit">Registrar</button>
            </form>
            <p>¿Ya tienes una cuenta? <Link to="/login">Iniciar sesión</Link></p>
        </div>
    );
}

export default RegistrationPage;
