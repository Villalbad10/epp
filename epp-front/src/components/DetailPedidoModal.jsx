import { useState, useEffect, useCallback } from 'react'
import axios from 'axios'
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  Grid,
  Divider,
  Typography,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Chip,
  Box,
  CircularProgress,
  Stack,
} from '@mui/material'
import {
  Business as BusinessIcon,
  LocationOn as LocationIcon,
  Science as ScienceIcon,
} from '@mui/icons-material'

const API_BASE_URL = 'http://127.0.0.1:8080/api/v1'

const DetailPedidoModal = ({ open, onClose, pedidoId }) => {
  const [pedido, setPedido] = useState(null)
  const [loading, setLoading] = useState(false)

  // Función para obtener color del estado
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

  // Función para formatear fecha
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

  // Fetch detalle de pedido
  const fetchPedidoDetail = useCallback(async (id) => {
    if (!id) return

    try {
      setLoading(true)
      const response = await axios.get(`${API_BASE_URL}/pedidos/${id}`)
      setPedido(response.data)
    } catch (err) {
      console.error('Error fetching pedido detail:', err)
      setPedido(null)
    } finally {
      setLoading(false)
    }
  }, [])

  // Cargar datos cuando se abre el modal
  useEffect(() => {
    if (open && pedidoId) {
      fetchPedidoDetail(pedidoId)
    } else if (!open) {
      // Limpiar datos cuando se cierra el modal
      setPedido(null)
    }
  }, [open, pedidoId, fetchPedidoDetail])

  return (
    <Dialog open={open} onClose={onClose} maxWidth="md" fullWidth>
      <DialogTitle sx={{ pb: 1 }}>
        <Box display="flex" justifyContent="space-between" alignItems="center">
          <Typography variant="h6" component="div" fontWeight="bold">
            Detalle del Pedido
          </Typography>
          {pedido && (
            <Chip
              label={pedido.estado}
              color={getEstadoColor(pedido.estado)}
              size="small"
              sx={{ fontWeight: 'bold' }}
            />
          )}
        </Box>
      </DialogTitle>
      <DialogContent dividers sx={{ pt: 2 }}>
        {loading ? (
          <Box display="flex" justifyContent="center" alignItems="center" p={4}>
            <CircularProgress />
          </Box>
        ) : pedido ? (
          <Stack spacing={2}>
            {/* Header con información principal del pedido - horizontal */}
            <Paper variant="outlined" sx={{ p: 1.5, bgcolor: 'background.default' }}>
              <Grid container spacing={2}>
                <Grid item xs={6} sm={3}>
                  <Typography variant="caption" color="text.secondary" display="block" gutterBottom>
                    Número de Pedido
                  </Typography>
                  <Typography variant="body2" fontWeight="medium">
                    {pedido.numeroPedido}
                  </Typography>
                </Grid>
                <Grid item xs={6} sm={3}>
                  <Typography variant="caption" color="text.secondary" display="block" gutterBottom>
                    Fecha
                  </Typography>
                  <Typography variant="body2" fontWeight="medium">
                    {formatDate(pedido.fechaPedido)}
                  </Typography>
                </Grid>
                <Grid item xs={6} sm={3}>
                  <Typography variant="caption" color="text.secondary" display="block" gutterBottom>
                    Total
                  </Typography>
                  <Typography variant="body1" color="primary" fontWeight="bold">
                    S/ {pedido.total?.toFixed(2) || '0.00'}
                  </Typography>
                </Grid>
                <Grid item xs={6} sm={3}>
                  <Typography variant="caption" color="text.secondary" display="block" gutterBottom>
                    Items
                  </Typography>
                  <Typography variant="body2" fontWeight="medium">
                    {pedido.items?.length || 0} producto(s)
                  </Typography>
                </Grid>
              </Grid>
            </Paper>

            {/* Información de Empresa - organizada horizontalmente */}
            <Paper variant="outlined" sx={{ p: 1.5 }}>
              <Stack direction="row" spacing={1} alignItems="center" mb={1}>
                <BusinessIcon color="primary" fontSize="small" />
                <Typography variant="subtitle2" fontWeight="bold">
                  Empresa
                </Typography>
              </Stack>
              <Divider sx={{ mb: 1.5 }} />
              <Grid container spacing={2}>
                <Grid item xs={12} sm={6}>
                  <Typography variant="caption" color="text.secondary" display="block" gutterBottom>
                    Razón Social
                  </Typography>
                  <Typography variant="body2" fontWeight="medium">
                    {pedido.empresa?.nombre || '-'}
                  </Typography>
                </Grid>
                <Grid item xs={12} sm={6}>
                  <Typography variant="caption" color="text.secondary" display="block" gutterBottom>
                    RUC
                  </Typography>
                  <Typography variant="body2">
                    {pedido.empresa?.ruc || '-'}
                  </Typography>
                </Grid>
                {pedido.empresa?.direccion && (
                  <Grid item xs={12} sm={4}>
                    <Typography variant="caption" color="text.secondary" display="block" gutterBottom>
                      Dirección
                    </Typography>
                    <Typography variant="body2">
                      {pedido.empresa.direccion}
                    </Typography>
                  </Grid>
                )}
                {pedido.empresa?.telefono && (
                  <Grid item xs={12} sm={4}>
                    <Typography variant="caption" color="text.secondary" display="block" gutterBottom>
                      Teléfono
                    </Typography>
                    <Typography variant="body2">
                      {pedido.empresa.telefono}
                    </Typography>
                  </Grid>
                )}
                {pedido.empresa?.email && (
                  <Grid item xs={12} sm={4}>
                    <Typography variant="caption" color="text.secondary" display="block" gutterBottom>
                      Email
                    </Typography>
                    <Typography variant="body2">
                      {pedido.empresa.email}
                    </Typography>
                  </Grid>
                )}
              </Grid>
            </Paper>

            {/* Área, Producto Químico y Observaciones - organizados horizontalmente */}
            <Paper variant="outlined" sx={{ p: 1.5 }}>
              <Grid container spacing={2}>
                {/* Área */}
                <Grid item xs={12} sm={4}>
                  <Stack direction="row" spacing={0.5} alignItems="center" mb={0.5}>
                    <LocationIcon color="primary" fontSize="small" />
                    <Typography variant="caption" fontWeight="bold" color="text.secondary">
                      ÁREA
                    </Typography>
                  </Stack>
                  <Typography variant="body2" fontWeight="medium">
                    {pedido.area?.nombre || '-'}
                  </Typography>
                  {pedido.area?.descripcion && (
                    <Typography variant="caption" color="text.secondary" display="block" sx={{ mt: 0.5 }}>
                      {pedido.area.descripcion}
                    </Typography>
                  )}
                </Grid>

                {/* Producto Químico */}
                <Grid item xs={12} sm={4}>
                  <Stack direction="row" spacing={0.5} alignItems="center" mb={0.5}>
                    <ScienceIcon color="primary" fontSize="small" />
                    <Typography variant="caption" fontWeight="bold" color="text.secondary">
                      PRODUCTO QUÍMICO
                    </Typography>
                  </Stack>
                  <Typography variant="body2" fontWeight="medium">
                    {pedido.productoQuimico?.nombre || '-'}
                  </Typography>
                  <Typography variant="caption" color="text.secondary" display="block" sx={{ mt: 0.5 }}>
                    Código: {pedido.productoQuimico?.codigo || '-'}
                  </Typography>
                </Grid>

                {/* Observaciones */}
                {pedido.observaciones && (
                  <Grid item xs={12} sm={4}>
                    <Typography variant="caption" fontWeight="bold" color="text.secondary" display="block" gutterBottom>
                      OBSERVACIONES
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      {pedido.observaciones}
                    </Typography>
                  </Grid>
                )}
              </Grid>
            </Paper>

            {/* Items del Pedido */}
            <Paper variant="outlined" sx={{ p: 1.5 }}>
              <Typography variant="subtitle2" fontWeight="bold" gutterBottom>
                Items del Pedido
              </Typography>
              <TableContainer sx={{ mt: 1 }}>
                <Table size="small">
                  <TableHead>
                    <TableRow sx={{ bgcolor: 'action.hover' }}>
                      <TableCell padding="normal" sx={{ py: 1 }}><strong>EPP</strong></TableCell>
                      <TableCell align="right" padding="normal" sx={{ py: 1 }}><strong>Cantidad</strong></TableCell>
                      <TableCell align="right" padding="normal" sx={{ py: 1 }}><strong>Precio Unit.</strong></TableCell>
                      <TableCell align="right" padding="normal" sx={{ py: 1 }}><strong>Subtotal</strong></TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {pedido.items?.map((item, index) => (
                      <TableRow 
                        key={index}
                        sx={{ 
                          '&:hover': { bgcolor: 'action.hover' }
                        }}
                      >
                        <TableCell padding="normal" sx={{ py: 0.75 }}>
                          <Typography variant="body2" fontWeight="medium">
                            {item.epp?.nombre || '-'}
                          </Typography>
                          <Typography variant="caption" color="text.secondary">
                            {item.epp?.codigo || '-'}
                          </Typography>
                        </TableCell>
                        <TableCell align="right" padding="normal" sx={{ py: 0.75 }}>
                          <Typography variant="body2">
                            {item.cantidad}
                          </Typography>
                        </TableCell>
                        <TableCell align="right" padding="normal" sx={{ py: 0.75 }}>
                          <Typography variant="body2">
                            S/ {item.precioUnitario?.toFixed(2) || '0.00'}
                          </Typography>
                        </TableCell>
                        <TableCell align="right" padding="normal" sx={{ py: 0.75 }}>
                          <Typography variant="body2" fontWeight="medium">
                            S/ {item.subtotal?.toFixed(2) || '0.00'}
                          </Typography>
                        </TableCell>
                      </TableRow>
                    ))}
                    <TableRow sx={{ bgcolor: 'action.selected', '& td': { borderTop: 2, borderColor: 'divider', py: 1 } }}>
                      <TableCell colSpan={3} align="right" padding="normal">
                        <Typography variant="subtitle1" fontWeight="bold">
                          Total:
                        </Typography>
                      </TableCell>
                      <TableCell align="right" padding="normal">
                        <Typography variant="subtitle1" fontWeight="bold" color="primary">
                          S/ {pedido.total?.toFixed(2) || '0.00'}
                        </Typography>
                      </TableCell>
                    </TableRow>
                  </TableBody>
                </Table>
              </TableContainer>
            </Paper>
          </Stack>
        ) : (
          <Box display="flex" justifyContent="center" p={4}>
            <Typography variant="body1" color="text.secondary">
              No se pudo cargar el detalle del pedido
            </Typography>
          </Box>
        )}
      </DialogContent>
      <DialogActions sx={{ p: 1.5 }}>
        <Button onClick={onClose} variant="contained" color="primary" size="medium">
          Cerrar
        </Button>
      </DialogActions>
    </Dialog>
  )
}

export default DetailPedidoModal

