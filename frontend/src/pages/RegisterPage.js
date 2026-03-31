import { useState } from "react";
import { register, saveMetadata } from "../services/api";
import "../styles/RegisterPage.css";

export default function RegisterPage() {
    const [step, setStep] = useState(1);
    const [userId, setUserId] = useState(null);

    const [form, setForm] = useState({
        name: "",
        email: "",
        password: ""
    });

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    const [metadata, setMetadata] = useState({
        tipoDocumento: "",
        numeroDocumento: ""
    });

    const handleRegister = async () => {
        setLoading(true);
        setError("");
        setSuccess("");

        try {
            const res = await register(form);

            if (res.error) {
                setError(res.error);
            } else {
                setSuccess(res.message || "Usuario creado correctamente");

                // Si tu backend devuelve userId, guárdalo
                if (res.userId) {
                    setUserId(res.userId);
                }

                setStep(2);
            }

        } catch (err) {
            setError("Error de conexión con el servidor");
        } finally {
            setLoading(false);
        }
    };

    const handleMetadata = async () => {
        setLoading(true);
        setError("");

        try {
            await saveMetadata(userId, metadata);
            setStep(3);
        } catch (err) {
            setError("Error guardando metadata");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="container">
            <h2>Registro</h2>

            {/* STEP 1 - REGISTRO */}
            {step === 1 && (
                <>
                    <input
                        type="text"
                        placeholder="Nombre"
                        value={form.name}
                        onChange={(e) =>
                            setForm({ ...form, name: e.target.value })
                        }
                    />

                    <input
                        type="email"
                        placeholder="Email"
                        value={form.email}
                        onChange={(e) =>
                            setForm({ ...form, email: e.target.value })
                        }
                    />

                    <input
                        type="password"
                        placeholder="Password"
                        value={form.password}
                        onChange={(e) =>
                            setForm({ ...form, password: e.target.value })
                        }
                    />

                    <button onClick={handleRegister} disabled={loading}>
                        {loading ? "Registrando..." : "Registrarse"}
                    </button>
                </>
            )}

            {/* STEP 2 - METADATA */}
            {step === 2 && (
                <>
                    <h3>Información adicional</h3>

                    <input
                        type="text"
                        placeholder="Tipo de documento"
                        value={metadata.tipoDocumento}
                        onChange={(e) =>
                            setMetadata({
                                ...metadata,
                                tipoDocumento: e.target.value
                            })
                        }
                    />

                    <input
                        type="text"
                        placeholder="Número de documento"
                        value={metadata.numeroDocumento}
                        onChange={(e) =>
                            setMetadata({
                                ...metadata,
                                numeroDocumento: e.target.value
                            })
                        }
                    />

                    <button onClick={handleMetadata} disabled={loading}>
                        {loading ? "Guardando..." : "Guardar metadata"}
                    </button>
                </>
            )}

            {/* STEP 3 - SUCCESS */}
            {step === 3 && (
                <h3>✅ Registro completo</h3>
            )}

            {error && <p className="error">{error}</p>}
            {success && <p className="success">{success}</p>}
        </div>
    );
}