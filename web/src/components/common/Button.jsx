export const Button = ({ children, onClick, type = 'button', variant = 'primary', disabled = false }) => {
  const variants = { primary: 'btn', danger: 'btn btn-danger', secondary: 'btn btn-secondary' };
  return (
    <button type={type} className={variants[variant]} onClick={onClick} disabled={disabled}>
      {children}
    </button>
  );
};