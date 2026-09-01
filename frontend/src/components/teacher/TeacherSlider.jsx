import { Swiper, SwiperSlide } from "swiper/react";
import { Autoplay, Navigation } from "swiper/modules";

import "swiper/css";
import "swiper/css/navigation";
import { API_URL } from "../../config/api";

export default function TeacherSlider({ teachers }) {
    return (
        <div className="container">

            <h2 className="title">Преподаватели</h2>

            <div className="teachers-slider-wrapper">

                <div className="slider-arrow custom-prev">‹</div>

                <Swiper
                    modules={[Autoplay, Navigation]}
                    navigation={{
                        nextEl: ".custom-next",
                        prevEl: ".custom-prev",
                    }}
                    loop={teachers.length > 3}
                    autoplay={{
                        delay: 2500,
                        disableOnInteraction: false,
                    }}
                    slidesPerView={3}
                    spaceBetween={24}
                    breakpoints={{
                        0: { slidesPerView: 1 },
                        700: { slidesPerView: 2 },
                        1100: { slidesPerView: 3 },
                    }}
                >
                    {teachers.map((t) => (
                        <SwiperSlide key={t.id}>
                            <div
                                className="teacher-card"
                                onClick={() => window.location.href = `mailto:${t.email}`}
                                style={{ cursor: "pointer" }}
                            >

                                <img
                                    src={
                                        t.avatarUrl
                                            ? `${API_URL}${t.avatarUrl}`
                                            : "/teachers/firefox.png"
                                    }
                                    onError={(e) => {
                                        e.target.src = "/teachers/firefox.png";
                                    }}
                                    className="teacher-image"
                                />

                                <div className="teacher-overlay">

                                    <div
                                        className="teacher-name"
                                        style={{ fontSize: "2rem", fontWeight: "700" }}
                                    >
                                        {t.name}
                                    </div>

                                    <div className="teacher-email">
                                        {t.email}
                                    </div>

                                </div>

                            </div>
                        </SwiperSlide>
                    ))}
                </Swiper>

                <div className="slider-arrow custom-next">›</div>
            </div>
        </div>

    );
}
