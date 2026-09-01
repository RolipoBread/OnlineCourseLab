import { useAuth } from "../../context/AuthContext";
import { useNavigate } from "react-router-dom";
import { useLocation, Link } from "react-router-dom";

export default function Header() {

    const { user, logoutUser } = useAuth();
    const navigate = useNavigate();

    const location = useLocation();
    const isProfilePage = location.pathname === "/profile";

    return (
        <div className="container">
            <div className="header">
                <div className="logo" onClick={() => navigate("/")}>Lab</div>

                <div className="nav">

                    <div className="nav-buttons">
                        {!isProfilePage && (
                            <>
                                {!user ? (
                                    <>
                                        <Link to="/login">
                                            <button className="login-btn">Войти</button>
                                        </Link>

                                        <Link to="/register">
                                            <button className="register-btn">Зарегистрироваться</button>
                                        </Link>
                                    </>
                                ) : (
                                    <div className="profile-wrapper">
                                        <div className="profile-wrapper" onClick={() => navigate("/profile")}>

                                            <div className="profile-circle">
                                                {user.avatarUrl ? (
                                                    <img
                                                        src={`http://localhost:8080${user.avatarUrl}`}
                                                        alt="аватар"
                                                    />
                                                ) : (
                                                    <span>
                {user.name?.charAt(0).toUpperCase()}
            </span>
                                                )}
                                            </div>
                                        </div>
                                    </div>
                                )}
                            </>
                        )}
                    </div>
                </div>

                <div className="header-divider" />
            </div>

            <div className="header-divider" />
        </div>
    );
}
