import { useState, useEffect, useCallback } from 'react'
import axios from 'axios'
import {
  ThemeProvider,
  createTheme,
  CssBaseline,
  Container,
  Typography,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  TablePagination,
  Box,
  CircularProgress,
  Alert,
  Chip,
  Card,
  CardContent,
  Button,
  IconButton,
} from '@mui/material'
import {
  Add as AddIcon,
  Visibility as VisibilityIcon,
} from '@mui/icons-material'
import CreatePedidoModal from './components/CreatePedidoModal'
import DetailPedidoModal from './components/DetailPedidoModal'
import SnackbarNotification from './components/SnackbarNotification'
import { SnackbarProvider, useSnackbar } from './context/SnackbarContext'
import './App.css'

const API_BASE_URL = 'http://127.0.0.1:8080/api/v1'

const theme = createTheme({
  palette: {
    mode: 'light',
  },
})

function AppContent() {
  // Estados principales
  const [pedidos, setPedidos] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [page, setPage] = useState(0)
  const [rowsPerPage, setRowsPerPage] = useState(10)
  const [totalElements, setTotalElements] = useState(0)

  // Estados para modales
  const [openCreateModal, setOpenCreateModal] = useState(false)
  const [openDetailModal, setOpenDetailModal] = useState(false)
  const [selectedPedidoId, setSelectedPedidoId] = useState(null)

  // Hook para snackbar
  const { showSnackbar } = useSnackbar()

  // Fetch pedidos
  const fetchPedidos = useCallback(async (pageNum, size) => {
    try {
      setLoading(true)
      setError(null)
      const response = await axios.get(`${API_BASE_URL}/pedidos/list`, {
        params: {
          page: pageNum,
          size: size,
          sort: 'id,desc',
        },
      })

      setPedidos(response.data.content)
      setTotalElements(response.data.totalElements)
    } catch (err) {
      setError(err.message || 'Error al cargar los pedidos')
      console.error('Error fetching pedidos:', err)
    } finally {
      setLoading(false)
    }
  }, [])

  // Refrescar lista de pedidos
  const refreshPedidos = useCallback(() => {
    fetchPedidos(page, rowsPerPage)
  }, [page, rowsPerPage, fetchPedidos])

  // Abrir modal de creación
  const handleOpenCreateModal = () => {
    setOpenCreateModal(true)
  }

  // Cerrar modal de creación
  const handleCloseCreateModal = () => {
    setOpenCreateModal(false)
  }

  // Manejar éxito al crear pedido
  const handleCreateSuccess = (message) => {
    showSnackbar(message, 'success')
    handleCloseCreateModal()
    refreshPedidos()
  }

  // Manejar error al crear pedido
  const handleCreateError = (message) => {
    showSnackbar(message, 'error')
  }

  // Abrir modal de detalle
  const handleOpenDetailModal = (pedido) => {
    setSelectedPedidoId(pedido.id)
    setOpenDetailModal(true)
  }

  // Cerrar modal de detalle
  const handleCloseDetailModal = () => {
    setOpenDetailModal(false)
    setSelectedPedidoId(null)
  }

  useEffect(() => {
    fetchPedidos(page, rowsPerPage)
  }, [page, rowsPerPage, fetchPedidos])

  const handleChangePage = (event, newPage) => {
    setPage(newPage)
  }

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10))
    setPage(0)
  }

  const getEstadoColor = (estado) => {
    switch (estado) {
      case 'PENDIENTE':
        return 'warning'
      case 'APROBADO':
        return 'success'
      case 'RECHAZADO':
        return 'error'
      default:
        return 'default'
    }
  }

  const formatDate = (dateString) => {
    if (!dateString) return '-'
    const date = new Date(dateString)
    return date.toLocaleDateString('es-ES', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit',
    })
  }

  return (
    <>
      <Container maxWidth="xl" sx={{ mt: 4, mb: 4 }}>
        <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
          <Typography variant="h4" component="h1">
            Lista de Pedidos
          </Typography>
          <Button
            variant="contained"
            startIcon={<AddIcon />}
            onClick={handleOpenCreateModal}
          >
            Nuevo Pedido
          </Button>
        </Box>

        {error && (
          <Alert severity="error" sx={{ mb: 2 }}>
            {error}
          </Alert>
        )}

        {loading ? (
          <Box display="flex" justifyContent="center" alignItems="center" minHeight="400px">
            <CircularProgress />
          </Box>
        ) : (
          <Card>
            <CardContent>
              <TableContainer component={Paper} variant="outlined">
                <Table>
                  <TableHead>
                    <TableRow>
                      <TableCell><strong>ID</strong></TableCell>
                      <TableCell><strong>Número de Pedido</strong></TableCell>
                      <TableCell><strong>Empresa</strong></TableCell>
                      <TableCell><strong>Área</strong></TableCell>
                      <TableCell><strong>Producto Químico</strong></TableCell>
                      <TableCell><strong>Fecha Pedido</strong></TableCell>
                      <TableCell><strong>Estado</strong></TableCell>
                      <TableCell align="right"><strong>Total</strong></TableCell>
                      <TableCell align="center"><strong>Acciones</strong></TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {pedidos.length === 0 ? (
                      <TableRow>
                        <TableCell colSpan={9} align="center">
                          No hay pedidos disponibles
                        </TableCell>
                      </TableRow>
                    ) : (
                      pedidos.map((pedido) => (
                        <TableRow key={pedido.id} hover>
                          <TableCell>{pedido.id}</TableCell>
                          <TableCell>{pedido.numeroPedido}</TableCell>
                          <TableCell>
                            {pedido.empresa?.nombre || '-'}
                            <br />
                            <Typography variant="caption" color="text.secondary">
                              NIT: {pedido.empresa?.nit || '-'}
                            </Typography>
                          </TableCell>
                          <TableCell>{pedido.area?.nombre || '-'}</TableCell>
                          <TableCell>{pedido.productoQuimico?.nombre || '-'}</TableCell>
                          <TableCell>{formatDate(pedido.fechaPedido)}</TableCell>
                          <TableCell>
                            <Chip
                              label={pedido.estado}
                              color={getEstadoColor(pedido.estado)}
                              size="small"
                            />
                          </TableCell>
                          <TableCell align="right">
                            <strong>S/ {pedido.total?.toFixed(2) || '0.00'}</strong>
                          </TableCell>
                          <TableCell align="center">
                            <IconButton
                              color="primary"
                              onClick={() => handleOpenDetailModal(pedido)}
                              size="small"
                            >
                              <VisibilityIcon />
                            </IconButton>
                          </TableCell>
                        </TableRow>
                      ))
                    )}
                  </TableBody>
                </Table>
              </TableContainer>

              <TablePagination
                component="div"
                count={totalElements}
                page={page}
                onPageChange={handleChangePage}
                rowsPerPage={rowsPerPage}
                onRowsPerPageChange={handleChangeRowsPerPage}
                rowsPerPageOptions={[5, 10, 25, 50]}
                labelRowsPerPage="Filas por página:"
                labelDisplayedRows={({ from, to, count }) =>
                  `${from}-${to} de ${count !== -1 ? count : `más de ${to}`}`
                }
              />
            </CardContent>
          </Card>
        )}
      </Container>

      {/* Modal de Crear Pedido */}
      <CreatePedidoModal
        open={openCreateModal}
        onClose={handleCloseCreateModal}
        onSuccess={handleCreateSuccess}
        onError={handleCreateError}
      />

      {/* Modal de Detalle de Pedido */}
      <DetailPedidoModal
        open={openDetailModal}
        onClose={handleCloseDetailModal}
        pedidoId={selectedPedidoId}
      />
    </>
  )
}

function App() {
  return (
    <SnackbarProvider>
      <ThemeProvider theme={theme}>
        <CssBaseline />
        <AppContent />
        <SnackbarNotification />
      </ThemeProvider>
    </SnackbarProvider>
  )
}

export default App
