import React from 'react'

const Icon = ({url,Icon}) => {
  return (        
    <a href={url} target="_blank" rel="noopener noreferrer">
    <Icon size={18} className='cursor-pointer' /> 
    </a> 
  )
}

export default Icon