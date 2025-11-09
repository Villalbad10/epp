import { Snackbar, Alert } from '@mui/material'
import { useSnackbar } from '../context/SnackbarContext'

const SnackbarNotification = () => {
  const { snackbar, hideSnackbar } = useSnackbar()

  return (
    <Snackbar
      open={snackbar.open}
      autoHideDuration={6000}
      onClose={hideSnackbar}
      anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
      sx={{
        top: { xs: '16px', sm: '24px' },
      }}
    >
      <Alert
        onClose={hideSnackbar}
        severity={snackbar.severity}
        sx={{ 
          width: '100%',
          minWidth: { xs: '300px', sm: '400px' },
          maxWidth: { xs: 'calc(100vw - 32px)', sm: '500px' },
        }}
        variant="filled"
      >
        {snackbar.message}
      </Alert>
    </Snackbar>
  )
}

export default SnackbarNotification

