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
  Divider,
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
      const response = await axios.get(`${API_BASE_URL}/empresas`)
      setEmpresas(Array.isArray(response.data) ? response.data : response.data.content || [])
    } catch (err) {
      console.error('Error fetching empresas:', err)
      onError('Error al cargar las empresas')
    }
  }, [onError])

  // Fetch áreas
  const fetchAreas = useCallback(async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/areas`)
      setAreas(Array.isArray(response.data) ? response.data : response.data.content || [])
    } catch (err) {
      console.error('Error fetching areas:', err)
      onError('Error al cargar las áreas')
    }
  }, [onError])

  // Fetch productos químicos
  const fetchProductosQuimicos = useCallback(async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/productos-quimicos`)
      setProductosQuimicos(Array.isArray(response.data) ? response.data : response.data.content || [])
    } catch (err) {
      console.error('Error fetching productos quimicos:', err)
      onError('Error al cargar los productos químicos')
    }
  }, [onError])

  // Fetch EPPs
  const fetchEpps = useCallback(async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/epp`)
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
        empresa: { id: parseInt(formData.empresaId) },
        area: { id: parseInt(formData.areaId) },
        productoQuimico: { id: parseInt(formData.productoQuimicoId) },
        observaciones: formData.observaciones,
        items: formData.items.map((item) => ({
          epp: { id: parseInt(item.eppId) },
          cantidad: parseInt(item.cantidad),
        })),
      }

      await axios.post(`${API_BASE_URL}/pedidos`, payload)
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
      <DialogTitle>Crear Nuevo Pedido</DialogTitle>
      <DialogContent>
        {loadingData ? (
          <Grid container spacing={2} sx={{ mt: 1 }}>
            <Grid item xs={12} sx={{ textAlign: 'center', py: 4 }}>
              <CircularProgress />
              <Typography variant="body2" color="text.secondary" sx={{ mt: 2 }}>
                Cargando datos...
              </Typography>
            </Grid>
          </Grid>
        ) : (
          <Grid container spacing={2} sx={{ mt: 1 }}>
            <Grid item xs={12}>
              <FormControl fullWidth error={!!formErrors.empresaId}>
                <InputLabel>Empresa *</InputLabel>
                <Select
                  value={formData.empresaId}
                  label="Empresa *"
                  onChange={(e) =>
                    setFormData({ ...formData, empresaId: e.target.value })
                  }
                >
                  {empresas.map((empresa) => (
                    <MenuItem key={empresa.id} value={empresa.id}>
                      {empresa.nombre} - {empresa.ruc}
                    </MenuItem>
                  ))}
                </Select>
                {formErrors.empresaId && (
                  <FormHelperText>{formErrors.empresaId}</FormHelperText>
                )}
              </FormControl>
            </Grid>

            <Grid item xs={12} sm={6}>
              <FormControl fullWidth error={!!formErrors.areaId}>
                <InputLabel>Área *</InputLabel>
                <Select
                  value={formData.areaId}
                  label="Área *"
                  onChange={(e) =>
                    setFormData({ ...formData, areaId: e.target.value })
                  }
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

            <Grid item xs={12} sm={6}>
              <FormControl fullWidth error={!!formErrors.productoQuimicoId}>
                <InputLabel>Producto Químico *</InputLabel>
                <Select
                  value={formData.productoQuimicoId}
                  label="Producto Químico *"
                  onChange={(e) =>
                    setFormData({ ...formData, productoQuimicoId: e.target.value })
                  }
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

            <Grid item xs={12}>
              <TextField
                fullWidth
                label="Observaciones"
                multiline
                rows={3}
                value={formData.observaciones}
                onChange={(e) =>
                  setFormData({ ...formData, observaciones: e.target.value })
                }
              />
            </Grid>

            <Grid item xs={12}>
              <Divider sx={{ my: 2 }} />
              <Typography variant="h6" gutterBottom>
                Items del Pedido
              </Typography>
            </Grid>

            <Grid item xs={12} sm={5}>
              <FormControl fullWidth>
                <InputLabel>EPP</InputLabel>
                <Select
                  value={newItem.eppId}
                  label="EPP"
                  onChange={(e) =>
                    setNewItem({ ...newItem, eppId: e.target.value })
                  }
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

            <Grid item xs={12} sm={4}>
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

            <Grid item xs={12} sm={3}>
              <Button
                fullWidth
                variant="outlined"
                startIcon={<AddCircleIcon />}
                onClick={handleAddItem}
                sx={{ height: '56px' }}
              >
                Agregar
              </Button>
            </Grid>

            {formErrors.items && (
              <Grid item xs={12}>
                <FormHelperText error>{formErrors.items}</FormHelperText>
              </Grid>
            )}

            {formData.items.length > 0 && (
              <Grid item xs={12}>
                <TableContainer component={Paper} variant="outlined">
                  <Table size="small">
                    <TableHead>
                      <TableRow>
                        <TableCell><strong>EPP</strong></TableCell>
                        <TableCell align="right"><strong>Cantidad</strong></TableCell>
                        <TableCell align="right"><strong>Precio Unitario</strong></TableCell>
                        <TableCell align="right"><strong>Subtotal</strong></TableCell>
                        <TableCell align="center"><strong>Acción</strong></TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {formData.items.map((item, index) => (
                        <TableRow key={index}>
                          <TableCell>{item.eppNombre}</TableCell>
                          <TableCell align="right">{item.cantidad}</TableCell>
                          <TableCell align="right">
                            S/ {item.precioUnitario?.toFixed(2)}
                          </TableCell>
                          <TableCell align="right">
                            S/ {item.subtotal?.toFixed(2)}
                          </TableCell>
                          <TableCell align="center">
                            <IconButton
                              color="error"
                              size="small"
                              onClick={() => handleRemoveItem(index)}
                            >
                              <DeleteIcon />
                            </IconButton>
                          </TableCell>
                        </TableRow>
                      ))}
                      <TableRow>
                        <TableCell colSpan={3} align="right">
                          <strong>Total:</strong>
                        </TableCell>
                        <TableCell align="right">
                          <strong>S/ {calculateTotal().toFixed(2)}</strong>
                        </TableCell>
                        <TableCell />
                      </TableRow>
                    </TableBody>
                  </Table>
                </TableContainer>
              </Grid>
            )}
          </Grid>
        )}
      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose} disabled={loadingForm}>
          Cancelar
        </Button>
        <Button
          onClick={handleCreatePedido}
          variant="contained"
          disabled={loadingForm || loadingData}
        >
          {loadingForm ? <CircularProgress size={24} /> : 'Crear Pedido'}
        </Button>
      </DialogActions>
    </Dialog>
  )
}

export default CreatePedidoModal

