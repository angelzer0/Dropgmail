import { Link } from 'react-router-dom';

const Inicio = () => {
  return (
    <div>
      <p>La mejor plataforma de cursos en línea</p>
      <Link to="/registro">Comenzar</Link>
    </div>
  );
};

export default Inicio;
