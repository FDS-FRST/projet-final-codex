export const Input = ({ label, type = 'text', value, onChange, required = false, ...props }) => (
  <div style={{ marginBottom: '1rem' }}>
    {label && <label style={{ display: 'block', marginBottom: '0.25rem' }}>{label}</label>}
    <input
      type={type}
      value={value}
      onChange={onChange}
      required={required}
      style={{ width: '100%', padding: '0.5rem', borderRadius: '0.375rem', border: '1px solid #ccc' }}
      {...props}
    />
  </div>
);