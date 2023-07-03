import { useUserContext } from "../../context/userContext";
import { Navigate } from "react-router-dom";

const RedirectUser = () => {
  const { token } = useUserContext();

  if (!token) return (
    <div className="bg-yellow-50 min-h-screen flex items-center min-w-full">
      <h2 className="font-Georgia font-bold text-5xl lg:text-6xl text-gray-800 text-center">
        No se puede redireccionar
      </h2>
    </div>
  );

  return <Navigate replace to={"/home"} />;
};

export default RedirectUser;