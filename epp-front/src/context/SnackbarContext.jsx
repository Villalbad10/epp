import { createContext, useContext, useState, useCallback } from 'react'

const SnackbarContext = createContext(null)

export const useSnackbar = () => {
  const context = useContext(SnackbarContext)
  if (!context) {
    throw new Error('useSnackbar debe ser usado dentro de SnackbarProvider')
  }
  return context
}

export const SnackbarProvider = ({ children }) => {
  const [snackbar, setSnackbar] = useState({
    open: false,
    message: '',
    severity: 'success',
  })

  const showSnackbar = useCallback((message, severity = 'success') => {
    setSnackbar({
      open: true,
      message,
      severity,
    })
  }, [])

  const hideSnackbar = useCallback(() => {
    setSnackbar((prev) => ({ ...prev, open: false }))
  }, [])

  const value = {
    snackbar,
    showSnackbar,
    hideSnackbar,
  }

  return (
    <SnackbarContext.Provider value={value}>
      {children}
    </SnackbarContext.Provider>
  )
}

