import { Outlet } from 'react-router-dom'
import NavMenuAdmin from "../components/custom-ui/NavMenuAdmin"
import HeaderAdmin from '../components/custom-ui/HeaderAdmin'

export default function Dashboard() {
  return (
    <div className="grid min-h-screen w-full md:grid-cols-[220px_1fr] lg:grid-cols-[280px_1fr]">
      <NavMenuAdmin/>
      <div className="flex flex-col ">
      <HeaderAdmin />
        <main className="flex flex-col gap-4 p-4 lg:gap-6 lg:p-6">
        <Outlet />
        </main>
      </div>
    </div>
  )
}
