// Modal.js
import React, { useState } from 'react';

const Modal = ({ isOpen, onClose, onSave, example }) => {
  const [localExample, setLocalExample] = useState(example);

  const handleSave = (e) => {
    try {
        e.preventDefault();
      JSON.parse(localExample); // Validate JSON
      onSave(localExample);
      onClose();
    } catch (e) {
        e.preventDefault();
      alert('Invalid JSON');
    }
  };

  return (
    <div
      className={`fixed inset-0 flex items-center justify-center bg-gray-800 bg-opacity-50 transition-opacity ${isOpen ? 'opacity-100' : 'opacity-0 pointer-events-none'}`}
      style={{ transition: 'opacity 0.3s ease' }}
    >
      <div className="p-6 rounded-lg shadow-lg w-96">
        <h2 className="text-xl font-semibold mb-4">Edit Example</h2>
        <textarea
          rows="10"
          className="w-full p-2 border rounded"
          value={localExample}
          onChange={(e) => setLocalExample(e.target.value)
        
          }
        />
        <div className="mt-4 flex justify-end">
          <button
            className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
            onClick={handleSave}
          >
            Save
          </button>
          <button
            className="ml-2 px-4 py-2 rounded hover:bg-gray-400"
            onClick={onClose}
          >
            Cancel
          </button>
        </div>
      </div>
    </div>
  );
};

export default Modal;
