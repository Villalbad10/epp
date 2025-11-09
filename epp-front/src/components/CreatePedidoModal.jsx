import { useState, useEffect, useCallback } from 'react'
import axios from 'axios'
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  TextField,
  MenuItem,
  Select,
  FormControl,
  InputLabel,
  IconButton,
  Grid,
  Typography,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  FormHelperText,
  CircularProgress,
  Stack,
  Box,
} from '@mui/material'
import {
  Delete as DeleteIcon,
  AddCircle as AddCircleIcon,
} from '@mui/icons-material'

const API_BASE_URL = 'http://127.0.0.1:8080/api/v1'

const CreatePedidoModal = ({ open, onClose, onSuccess, onError }) => {
  // Estados para formulario
  const [formData, setFormData] = useState({
    empresaId: '',
    areaId: '',
    productoQuimicoId: '',
    observaciones: '',
    items: [],
  })

  const [formErrors, setFormErrors] = useState({})
  const [loadingForm, setLoadingForm] = useState(false)

  // Estados para datos del formulario
  const [empresas, setEmpresas] = useState([])
  const [areas, setAreas] = useState([])
  const [productosQuimicos, setProductosQuimicos] = useState([])
  const [epps, setEpps] = useState([])
  const [loadingData, setLoadingData] = useState(false)

  // Estados para item temporal en formulario
  const [newItem, setNewItem] = useState({
    eppId: '',
    cantidad: '',
  })

  // Fetch empresas
  const fetchEmpresas = useCallback(async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/empresa/list`)
      setEmpresas(Array.isArray(response.data) ? response.data : response.data.content || [])
    } catch (err) {
      console.error('Error fetching empresas:', err)
      onError('Error al cargar las empresas')
    }
  }, [onError])

  // Fetch áreas
  const fetchAreas = useCallback(async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/area/list`)
      setAreas(Array.isArray(response.data) ? response.data : response.data.content || [])
    } catch (err) {
      console.error('Error fetching areas:', err)
      onError('Error al cargar las áreas')
    }
  }, [onError])

  // Fetch productos químicos
  const fetchProductosQuimicos = useCallback(async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/producto-quimico/list`)
      setProductosQuimicos(Array.isArray(response.data) ? response.data : response.data.content || [])
    } catch (err) {
      console.error('Error fetching productos quimicos:', err)
      onError('Error al cargar los productos químicos')
    }
  }, [onError])

  // Fetch EPPs
  const fetchEpps = useCallback(async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/epp/list`)
      setEpps(Array.isArray(response.data) ? response.data : response.data.content || [])
    } catch (err) {
      console.error('Error fetching EPPs:', err)
      onError('Error al cargar los EPPs')
    }
  }, [onError])

  // Cargar datos cuando se abre el modal
  useEffect(() => {
    if (open) {
      setLoadingData(true)
      Promise.all([
        fetchEmpresas(),
        fetchAreas(),
        fetchProductosQuimicos(),
        fetchEpps(),
      ]).finally(() => {
        setLoadingData(false)
      })
    }
  }, [open, fetchEmpresas, fetchAreas, fetchProductosQuimicos, fetchEpps])

  // Validar formulario
  const validateForm = () => {
    const errors = {}

    if (!formData.empresaId) {
      errors.empresaId = 'La empresa es requerida'
    }
    if (!formData.areaId) {
      errors.areaId = 'El área es requerida'
    }
    if (!formData.productoQuimicoId) {
      errors.productoQuimicoId = 'El producto químico es requerido'
    }
    if (formData.items.length === 0) {
      errors.items = 'Debe agregar al menos un item al pedido'
    }

    setFormErrors(errors)
    return Object.keys(errors).length === 0
  }

  // Crear pedido
  const handleCreatePedido = async () => {
    if (!validateForm()) {
      onError('Por favor complete todos los campos requeridos')
      return
    }

    try {
      setLoadingForm(true)
      const payload = {
        empresaId: parseInt(formData.empresaId),
        areaId: parseInt(formData.areaId),
        productoQuimicoId: parseInt(formData.productoQuimicoId),
        observaciones: formData.observaciones || null,
        items: formData.items.map((item) => ({
          eppId: parseInt(item.eppId),
          cantidad: parseInt(item.cantidad),
        })),
      }

      await axios.post(`${API_BASE_URL}/pedidos/save`, payload)
      onSuccess('Pedido creado exitosamente')
      handleClose()
    } catch (err) {
      onError(err.response?.data?.message || 'Error al crear el pedido')
      console.error('Error creating pedido:', err)
    } finally {
      setLoadingForm(false)
    }
  }

  // Resetear formulario
  const resetForm = () => {
    setFormData({
      empresaId: '',
      areaId: '',
      productoQuimicoId: '',
      observaciones: '',
      items: [],
    })
    setFormErrors({})
    setNewItem({
      eppId: '',
      cantidad: '',
    })
  }

  // Cerrar modal
  const handleClose = () => {
    resetForm()
    onClose()
  }

  // Agregar item al formulario
  const handleAddItem = () => {
    if (!newItem.eppId || !newItem.cantidad || parseInt(newItem.cantidad) <= 0) {
      onError('Seleccione un EPP y una cantidad válida')
      return
    }

    const epp = epps.find((e) => e.id === parseInt(newItem.eppId))
    if (!epp) return

    const item = {
      eppId: newItem.eppId,
      cantidad: newItem.cantidad,
      precioUnitario: epp.precioUnitario || 0,
      subtotal: (epp.precioUnitario || 0) * parseInt(newItem.cantidad),
      eppNombre: epp.nombre,
    }

    setFormData({
      ...formData,
      items: [...formData.items, item],
    })

    setNewItem({
      eppId: '',
      cantidad: '',
    })
  }

  // Eliminar item del formulario
  const handleRemoveItem = (index) => {
    const newItems = formData.items.filter((_, i) => i !== index)
    setFormData({
      ...formData,
      items: newItems,
    })
  }

  // Calcular total del formulario
  const calculateTotal = () => {
    return formData.items.reduce((sum, item) => sum + (item.subtotal || 0), 0)
  }

  return (
    <Dialog open={open} onClose={handleClose} maxWidth="md" fullWidth>
      <DialogTitle sx={{ pb: 1 }}>
        <Typography variant="h6" component="div" fontWeight="bold">
          Crear Nuevo Pedido
        </Typography>
      </DialogTitle>
      <DialogContent dividers sx={{ pt: 2 }}>
        {loadingData ? (
          <Box display="flex" flexDirection="column" alignItems="center" justifyContent="center" py={4}>
            <CircularProgress />
            <Typography variant="body2" color="text.secondary" sx={{ mt: 2 }}>
              Cargando datos...
            </Typography>
          </Box>
        ) : (
          <Stack spacing={3}>
            {/* Sección: Información del Pedido */}
            <Paper variant="outlined" sx={{ p: 2, bgcolor: 'background.default' }}>
              <Typography variant="subtitle1" fontWeight="bold" gutterBottom>
                Información del Pedido
              </Typography>
              <Grid container spacing={2} sx={{ mt: 0.5 }}>
                <Grid size={{ xs: 12 }}>
                  <FormControl fullWidth error={!!formErrors.empresaId}>
                    <InputLabel>Empresa *</InputLabel>
                    <Select
                      value={formData.empresaId}
                      label="Empresa *"
                      onChange={(e) =>
                        setFormData({ ...formData, empresaId: e.target.value })
                      }
                      sx={{ minWidth: 200 }}
                    >
                      {empresas.map((empresa) => (
                        <MenuItem key={empresa.id} value={empresa.id}>
                          {empresa.nombre} - {empresa.nit}
                        </MenuItem>
                      ))}
                    </Select>
                    {formErrors.empresaId && (
                      <FormHelperText>{formErrors.empresaId}</FormHelperText>
                    )}
                  </FormControl>
                </Grid>

                <Grid size={{ xs: 12, md: 6 }}>
                  <FormControl fullWidth error={!!formErrors.areaId}>
                    <InputLabel>Área *</InputLabel>
                    <Select
                      value={formData.areaId}
                      label="Área *"
                      onChange={(e) =>
                        setFormData({ ...formData, areaId: e.target.value })
                      }
                      sx={{ minWidth: 200 }}
                    >
                      {areas.map((area) => (
                        <MenuItem key={area.id} value={area.id}>
                          {area.nombre}
                        </MenuItem>
                      ))}
                    </Select>
                    {formErrors.areaId && (
                      <FormHelperText>{formErrors.areaId}</FormHelperText>
                    )}
                  </FormControl>
                </Grid>

                <Grid size={{ xs: 12, md: 6 }}>
                  <FormControl fullWidth error={!!formErrors.productoQuimicoId}>
                    <InputLabel>Producto Químico *</InputLabel>
                    <Select
                      value={formData.productoQuimicoId}
                      label="Producto Químico *"
                      onChange={(e) =>
                        setFormData({ ...formData, productoQuimicoId: e.target.value })
                      }
                      sx={{ minWidth: 200 }}
                    >
                      {productosQuimicos.map((producto) => (
                        <MenuItem key={producto.id} value={producto.id}>
                          {producto.nombre}
                        </MenuItem>
                      ))}
                    </Select>
                    {formErrors.productoQuimicoId && (
                      <FormHelperText>{formErrors.productoQuimicoId}</FormHelperText>
                    )}
                  </FormControl>
                </Grid>

                <Grid size={{ xs: 12 }}>
                  <TextField
                    fullWidth
                    label="Observaciones"
                    multiline
                    rows={2}
                    value={formData.observaciones}
                    onChange={(e) =>
                      setFormData({ ...formData, observaciones: e.target.value })
                    }
                    placeholder="Ingrese observaciones adicionales (opcional)"
                  />
                </Grid>
              </Grid>
            </Paper>

            {/* Sección: EPPs del Pedido */}
            <Paper variant="outlined" sx={{ p: 2 }}>
              <Typography variant="subtitle1" fontWeight="bold" gutterBottom>
                EPPs del Pedido
              </Typography>
              
              {/* Formulario para agregar items */}
              <Grid container spacing={2} sx={{ mt: 1, mb: 2 }}>
                <Grid size={{ xs: 12, md: 6 }}>
                  <FormControl fullWidth>
                    <InputLabel>Seleccionar EPP</InputLabel>
                    <Select
                      value={newItem.eppId}
                      label="Seleccionar EPP"
                      onChange={(e) =>
                        setNewItem({ ...newItem, eppId: e.target.value })
                      }
                      sx={{ minWidth: 200 }}
                    >
                      {epps
                        .filter((epp) => epp.activo !== false)
                        .map((epp) => (
                          <MenuItem key={epp.id} value={epp.id}>
                            {epp.nombre} - S/ {epp.precioUnitario?.toFixed(2)}
                          </MenuItem>
                        ))}
                    </Select>
                  </FormControl>
                </Grid>

                <Grid size={{ xs: 12, sm: 6, md: 3 }}>
                  <TextField
                    fullWidth
                    label="Cantidad"
                    type="number"
                    value={newItem.cantidad}
                    onChange={(e) =>
                      setNewItem({ ...newItem, cantidad: e.target.value })
                    }
                    inputProps={{ min: 1 }}
                  />
                </Grid>

                <Grid size={{ xs: 12, sm: 6, md: 3 }}>
                  <Button
                    fullWidth
                    variant="contained"
                    startIcon={<AddCircleIcon />}
                    onClick={handleAddItem}
                    sx={{ height: '56px' }}
                    disabled={!newItem.eppId || !newItem.cantidad}
                  >
                    Agregar
                  </Button>
                </Grid>
              </Grid>

              {formErrors.items && (
                <FormHelperText error sx={{ mb: 1 }}>
                  {formErrors.items}
                </FormHelperText>
              )}

              {/* Tabla de items agregados */}
              {formData.items.length > 0 ? (
                <TableContainer>
                  <Table size="small">
                    <TableHead>
                      <TableRow sx={{ bgcolor: 'action.hover' }}>
                        <TableCell><strong>EPP</strong></TableCell>
                        <TableCell align="right"><strong>Cantidad</strong></TableCell>
                        <TableCell align="right"><strong>Precio Unitario</strong></TableCell>
                        <TableCell align="right"><strong>Subtotal</strong></TableCell>
                        <TableCell align="center" width={80}><strong>Acción</strong></TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {formData.items.map((item, index) => (
                        <TableRow 
                          key={index}
                          sx={{ '&:hover': { bgcolor: 'action.hover' } }}
                        >
                          <TableCell>
                            <Typography variant="body2" fontWeight="medium">
                              {item.eppNombre}
                            </Typography>
                          </TableCell>
                          <TableCell align="right">
                            <Typography variant="body2">
                              {item.cantidad}
                            </Typography>
                          </TableCell>
                          <TableCell align="right">
                            <Typography variant="body2">
                              S/ {item.precioUnitario?.toFixed(2)}
                            </Typography>
                          </TableCell>
                          <TableCell align="right">
                            <Typography variant="body2" fontWeight="medium">
                              S/ {item.subtotal?.toFixed(2)}
                            </Typography>
                          </TableCell>
                          <TableCell align="center">
                            <IconButton
                              color="error"
                              size="small"
                              onClick={() => handleRemoveItem(index)}
                            >
                              <DeleteIcon fontSize="small" />
                            </IconButton>
                          </TableCell>
                        </TableRow>
                      ))}
                      <TableRow sx={{ bgcolor: 'action.selected', '& td': { borderTop: 2, borderColor: 'divider', py: 1.5 } }}>
                        <TableCell colSpan={3} align="right">
                          <Typography variant="subtitle1" fontWeight="bold">
                            Total:
                          </Typography>
                        </TableCell>
                        <TableCell align="right">
                          <Typography variant="subtitle1" fontWeight="bold" color="primary">
                            S/ {calculateTotal().toFixed(2)}
                          </Typography>
                        </TableCell>
                        <TableCell />
                      </TableRow>
                    </TableBody>
                  </Table>
                </TableContainer>
              ) : (
                <Box 
                  sx={{ 
                    p: 3, 
                    textAlign: 'center', 
                    bgcolor: 'action.hover', 
                    borderRadius: 1,
                    border: '1px dashed',
                    borderColor: 'divider'
                  }}
                >
                  <Typography variant="body2" color="text.secondary">
                    No hay items agregados. Seleccione un EPP, ingrese la cantidad y haga clic en "Agregar".
                  </Typography>
                </Box>
              )}
            </Paper>
          </Stack>
        )}
      </DialogContent>
      <DialogActions sx={{ p: 2, gap: 1 }}>
        <Button onClick={handleClose} disabled={loadingForm}>
          Cancelar
        </Button>
        <Button
          onClick={handleCreatePedido}
          variant="contained"
          disabled={loadingForm || loadingData}
          size="large"
        >
          {loadingForm ? <CircularProgress size={24} /> : 'Crear Pedido'}
        </Button>
      </DialogActions>
    </Dialog>
  )
}

export default CreatePedidoModal

